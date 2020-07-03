/*
 * COPYRIGHT. China Systems Co., Ltd. 2020. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of China Systems Co., Ltd.
 */
package com.donald.server.maintainer.task;


public class TaskItem {

    private String name;

    private String pid;

    private String memory;


    public String getName() {

        return this.name;
    }


    public void setName(final String name) {

        this.name = name;
    }


    public String getPid() {

        return this.pid;
    }


    public void setPid(final String pid) {

        this.pid = pid;
    }


    public String getMemory() {

        return this.memory;
    }


    public void setMemory(final String memory) {

        this.memory = memory;
    }

    @Override
    public String toString() {

        return this.name + " - " + this.pid;
    }

}
