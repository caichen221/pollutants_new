package com.iscas.common.tools.core.classloader;

import org.apache.commons.collections4.MapUtils;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 提供Jar隔离的加载机制，会把传入的路径、及其子路径、以及路径中的jar文件加入到class path。
 * 破坏双亲委派机制，改为逆向
 * */
public class JarLoader extends URLClassLoader {
    private static ThreadLocal<URL[]> threadLocal = new ThreadLocal<>();
    private URL[] allUrl;
    private boolean useCache = false;
    private String[] paths;
    private String pathStr;

    //缓存对象
    private static Map<String, Map<String, byte[]>> classBytes = new ConcurrentHashMap<>();


    public JarLoader(String[] paths, boolean useCache) {
        this(paths, JarLoader.class.getClassLoader(), useCache);
    }

    public JarLoader(String[] paths, ClassLoader parent, boolean useCache) {
        super(getURLs(paths), parent);
        //暂时先这样
        allUrl = threadLocal.get();
        this.useCache = useCache;
        this.paths = paths;
        StringBuilder pathBuilder = new StringBuilder();
        for (String path : paths) {
            pathBuilder.append(path).append(";");
        }
        pathStr = pathBuilder.toString();

    }

    public JarLoader(String[] paths) {
        this(paths, JarLoader.class.getClassLoader(), false);
    }

    public JarLoader(String[] paths, ClassLoader parent) {
        this (paths, parent, false);
    }

    /**
     * 清除某个路径下的缓存，
     * 可适用于不想重启服务，但更新了外部插件的jar包的情况下调用
     * */
    public static void clearCache(String[] paths) {
        StringBuilder pathBuilder = new StringBuilder();
        for (String path : paths) {
            pathBuilder.append(path).append(";");
        }
        classBytes.remove(pathBuilder.toString());
    }


    /**
     * 加载class文件，方便加载的方法
     * */
    public static Class<?> outerLoadClass(String name) throws ClassNotFoundException {
        return Thread.currentThread().getContextClassLoader().loadClass(name);
    }

    private static URL[] getURLs(String[] paths) {
        if (null == paths || 0 == paths.length) {
            throw new RuntimeException("jar包路径不能为空.");
        }

        List<File> jarFiles = new ArrayList<>();
        List<String> dirFiles = new ArrayList<>();
        for (String path : paths) {
            File file = new File(path);
            if (file.isFile()) {
                jarFiles.add(file);
            } else {
                dirFiles.add(path);
            }
        }

        List<String> dirs = new ArrayList<String>();
        for (String path : dirFiles) {
            dirs.add(path);
            JarLoader.collectDirs(path, dirs);
        }

        List<URL> urls = new ArrayList<URL>();
        for (String path : dirs) {
            urls.addAll(doGetURLs(path));
        }

        for (File jarFile : jarFiles) {
            try {
                URL url = jarFile.toURI().toURL();
                urls.add(url);
            } catch (Exception e) {
                throw new RuntimeException("系统加载jar包出错", e);
            }

        }

        URL[] urls1 = urls.toArray(new URL[0]);
        threadLocal.set(urls1);
        return urls1;
    }

    private static void collectDirs(String path, List<String> collector) {
        if (null == path || "".equalsIgnoreCase(path)) {
            return;
        }

        File current = new File(path);
        if (!current.exists() || !current.isDirectory()) {
            return;
        }

        for (File child : current.listFiles()) {
            if (!child.isDirectory()) {
                continue;
            }

            collector.add(child.getAbsolutePath());
            collectDirs(child.getAbsolutePath(), collector);
        }
    }

    private static List<URL> doGetURLs(final String path) {
        if (null == path || "".equalsIgnoreCase(path)) {
            throw new RuntimeException("jar包路径不能为空.");
        }
        File jarPath = new File(path);

        if (!jarPath.exists() || !jarPath.isDirectory()) {
            throw new RuntimeException("jar包路径必须存在且为目录.");
        }

        /* set filter */
        FileFilter jarFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".jar");
            }
        };

        /* iterate all jar */
        File[] allJars = new File(path).listFiles(jarFilter);
        List<URL> jarURLs = new ArrayList<URL>(allJars.length);

        for (int i = 0; i < allJars.length; i++) {
            try {
                jarURLs.add(allJars[i].toURI().toURL());
            } catch (Exception e) {
                throw new RuntimeException("系统加载jar包出错", e);
            }
        }
        return jarURLs;
    }
    //破坏双亲委派模型,采用逆向双亲委派
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> aClass = findClass(name);
        if (aClass == null) {
            return super.loadClass(name);
        }
        return aClass;
    }

    @Override
    public Class<?> findClass(String name) {

        //如果开启了缓存，查看class文件对应字节数组有没有缓存起来，如果有缓存，直接使用缓存的字节数组
        if (useCache) {
            synchronized (name.intern()) {
                Map<String, byte[]> cacheMap = classBytes.get(pathStr);
                if (MapUtils.isNotEmpty(cacheMap)) {
                    byte[] bytes = cacheMap.get(name);
                    if (bytes != null) {
                       Class<?> aClassx = this.defineClass(name, bytes, 0, bytes.length);
                       if (aClassx != null) {
                           System.out.println("读取到缓存.....");
                           return aClassx;
                       }
                    }
                }
            }
        }

        Class<?> aClass = null;
        if (allUrl != null) {
            String classPath = name.replace(".", "/");
            classPath = classPath.concat(".class");

            for (URL url : allUrl) {
                byte[] data = null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream is = null;
                try {
                    File file = new File(url.toURI());
                    if (file != null && file.exists()) {
                        JarFile jarFile = new JarFile(file);
                        if (jarFile != null) {
                            JarEntry jarEntry = jarFile.getJarEntry(classPath);
                            if (jarEntry != null) {
                                is = jarFile.getInputStream(jarEntry);
                                int c = 0;
                                byte[] buff = new byte[4096];
                                while (-1 != (c = is.read(buff))) {
                                    baos.write(buff, 0, c);
                                }
                                data = baos.toByteArray();
                                aClass = this.defineClass(name, data, 0, data.length);

                                synchronized (name.intern()) {
                                    if (useCache && aClass != null) {
                                        System.out.println("写入缓存---");
                                        Map<String, byte[]> classByteMap = classBytes.get(pathStr);
                                        if (MapUtils.isEmpty(classByteMap)) {
                                            classByteMap = new ConcurrentHashMap<>();
                                            classBytes.put(pathStr, classByteMap);
                                        }
                                        classBytes.get(pathStr).put(name, data);
                                    }
                                }

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        baos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return aClass;
    }

}
