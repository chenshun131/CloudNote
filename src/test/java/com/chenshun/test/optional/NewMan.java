package com.chenshun.test.optional;

import java.util.Optional;

/**
 * User: chenshun131 <p />
 * Time: 17/9/26 22:20  <p />
 * Version: V1.0  <p />
 * Description: 注意：Optional 不能被序列化 <p />
 */
public class NewMan {

    private Optional<Godness> godness = Optional.empty();

    private Godness god;

    public Optional<Godness> getGod() {
        return Optional.of(god);
    }

    public NewMan() {
    }

    public NewMan(Optional<Godness> godness) {
        this.godness = godness;
    }

    public Optional<Godness> getGodness() {
        return godness;
    }

    public void setGodness(Optional<Godness> godness) {
        this.godness = godness;
    }

    @Override
    public String toString() {
        return "NewMan [godness=" + godness + "]";
    }

}
