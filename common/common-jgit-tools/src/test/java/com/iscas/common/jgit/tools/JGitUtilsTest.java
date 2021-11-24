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
    public void diff() throws GitAPIException, IOException {
        List<String> diffs = JGitUtils.diff("a642251c32b92d8379ccb52a76a837007bccf915", "347135762942deddd5793f94168162f47372052b",
                "d:/tmp/jgit");
        System.out.println(diffs);
    }
}