package com.youxi912.yule912.model;

public class UpgradeModel {

    /**
     * name : 章鱼娱乐
     * version : 6
     * changelog : null
     * updated_at : 1535360484
     * versionShort : 2.0.0
     * build : 6
     * installUrl : http://download.fir.im/v2/app/install/5b759710959d6926d36604ad?download_token=063b761630620dc35aa36a491d9467cb&source=update
     * install_url : http://download.fir.im/v2/app/install/5b759710959d6926d36604ad?download_token=063b761630620dc35aa36a491d9467cb&source=update
     * direct_install_url : http://download.fir.im/v2/app/install/5b759710959d6926d36604ad?download_token=063b761630620dc35aa36a491d9467cb&source=update
     * update_url : http://fir.im/ul2y
     * binary : {"fsize":15108885}
     */

    private String name;
    private String version;
    private String changelog;//更新日志
    private long updated_at;
    private String versionShort;//版本编号(兼容旧版字段)
    private String build;//编译号
    private String installUrl;//安装地址（兼容旧版字段）
    private String install_url;//安装地址(新增字段)
    private String direct_install_url;
    private String update_url;//更新地址(新增字段)
    private BinaryBean binary;//更新文件的对象，仅有大小字段fsize

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getInstall_url() {
        return install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public String getDirect_install_url() {
        return direct_install_url;
    }

    public void setDirect_install_url(String direct_install_url) {
        this.direct_install_url = direct_install_url;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public BinaryBean getBinary() {
        return binary;
    }

    public void setBinary(BinaryBean binary) {
        this.binary = binary;
    }

    public static class BinaryBean {
        /**
         * fsize : 15108885
         */

        private int fsize;

        public int getFsize() {
            return fsize;
        }

        public void setFsize(int fsize) {
            this.fsize = fsize;
        }
    }
}
