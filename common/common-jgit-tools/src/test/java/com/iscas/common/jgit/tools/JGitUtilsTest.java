package com.iscas.common.jgit.tools;

import com.iscas.common.jgit.tools.exception.JGitException;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/11/24 10:31
 * @since jdk1.8
 */
public class JGitUtilsTest {

    @Test
    public void initTest() throws JGitException {
        JGitUtils.gitInit("d:/tmp/jgit");
    }

    @Test
    public void cloneTest() throws JGitException {
        JGitUtils.gitClone("http://172.16.10.190:8090/zhuquanwen/data-collect.git",
                "d:/tmp/jgit", "master", "zhuquanwen", "zhuquanwen");
    }

    @Test
    public void addTest() throws JGitException {
        JGitUtils.gitAdd("aaa.txt", "D:/tmp/jgit");
    }

    @Test
    public void commitTest() throws JGitException {
        JGitUtils.gitCommit("commit3!!", "D:/tmp/jgit");
    }

    @Test
    public void pullTest() throws JGitException {
        JGitUtils.gitPull("master", "D:/tmp/jgit");
    }

    @Test
    public void pushTest() throws JGitException {
        JGitUtils.gitPush("http://172.16.10.190:8090/zhuquanwen/data-collect.git", "D:/tmp/jgit",
                "master", "zhuquanwen", "zhuquanwen");
    }

    @Test
    public void branchATest() throws JGitException {
        List<Ref> refs = JGitUtils.gitBranchA("d:/tmp/jgit");
        for (Ref ref : refs) {
            System.out.println("ref:" + ref);
            System.out.println("name:" + ref.getName());
            System.out.println("leafName:" + ref.getLeaf().getName());
            System.out.println("targetName:" + ref.getTarget().getName());
            System.out.println("objectId:" + ref.getObjectId());
        }
    }

    @Test
    public void branchRTest() throws JGitException {
        List<Ref> refs = JGitUtils.gitBranchR("d:/tmp/jgit");
        for (Ref ref : refs) {
            System.out.println("ref:" + ref);
            System.out.println("name:" + ref.getName());
            System.out.println("leafName:" + ref.getLeaf().getName());
            System.out.println("targetName:" + ref.getTarget().getName());
            System.out.println("objectId:" + ref.getObjectId());
        }
    }

    @Test
    public void branchTest() throws JGitException {
        List<Ref> refs = JGitUtils.gitBranch("d:/tmp/jgit");
        for (Ref ref : refs) {
            System.out.println("ref:" + ref);
            System.out.println("name:" + ref.getName());
            System.out.println("leafName:" + ref.getLeaf().getName());
            System.out.println("targetName:" + ref.getTarget().getName());
            System.out.println("objectId:" + ref.getObjectId());
        }
    }

    @Test
    public void checkoutTest() throws JGitException {
        Ref ref = JGitUtils.gitCheckout("d:/tmp/jgit", "test");
        System.out.println("ref:" + ref);
        System.out.println("name:" + ref.getName());
        System.out.println("leafName:" + ref.getLeaf().getName());
        System.out.println("targetName:" + ref.getTarget().getName());
        System.out.println("objectId:" + ref.getObjectId());
    }

    @Test
    public void checkoutBTest() throws JGitException {
        Ref ref = JGitUtils.gitCheckoutB("d:/tmp/jgit", "test");
        System.out.println("ref:" + ref);
        System.out.println("name:" + ref.getName());
        System.out.println("leafName:" + ref.getLeaf().getName());
        System.out.println("targetName:" + ref.getTarget().getName());
        System.out.println("objectId:" + ref.getObjectId());
    }

    @Test
    public void statusTest() throws JGitException {
        Status status = JGitUtils.gitStatus("d:/tmp/jgit");
        System.out.println(status);
    }

    @Test
    public void logTest() throws JGitException {
        Iterable<RevCommit> revCommits = JGitUtils.gitLog("D:/tmp/jgit");
        Iterator<RevCommit> iterator = revCommits.iterator();
        while (iterator.hasNext()) {
            RevCommit next = iterator.next();
            System.out.println("commitinfo:" + next);
            System.out.println("commit id:" + next.getId());
            System.out.println("commit name:" + next.getName());
            System.out.println("commit time:" + next.getCommitTime());
            System.out.println("commit fullMessage:" + next.getFullMessage());
            System.out.println("commit shortMessage:" + next.getShortMessage());

        }
    }

    @Test
    public void diff() throws JGitException {
        List<String> diffs = JGitUtils.diff("eb53e57f8fdab0fc9ee3115b604f702e6a3f0bf0", "50fcc23cbb8b6234c74cac836022c0e20895a6eb",
                "C:\\ideaProjects\\newframe\\common\\common-jgit-tools\\tmp\\111");
        System.out.println(diffs);
    }

    @Test
    public void compare() throws JGitException {
        JGitUtils.compare("现在就可以下载镜像测试啦（此镜像为已经打包好的）\n" +
                "[root@localhost registry-data]# docker pull 192.168.100.94:5000/nginx:1.12.0\n" +
                "1.12.0: Pulling from nginx\n" +
                "177c8d195b28: Pull complete \n" +
                "80407d76f511: Pull complete \n" +
                "fa697bbf7113: Pull complete \n" +
                "Digest: sha256:aafd24200549cb5e06f911ed2f174ca5691901544cf4daa364339bcb7cff535e\n" +
                "Status: Downloaded newer image for 192.168.100.94:5000/nginx:1.12.0",
                "现在就可以下载镜像测试啦（此镜像为已经打包好的）\n" +
                        "\n" +
                        "[root@localhost registry-data]# docker pull 192.168.100.94:5000/nginx:1.12.0\n" +
                        "1.12.0: Pulling from nginx\n" +
                        "177c8d195b28: Pull complete \n" +
                        "80407d76f511: Pull complete \n" +
                        "fa697bbf7113: Pull complete AO \n" +
                        "Digest: sha256:aafd24200549cb5e06f911ed2f174ca5691901544cf4daa364339bcb7cff535e\n" +
                        "Status: Downloaded newer image for 192.168.100.94:5000/nginx:1.12.1");
    }

}