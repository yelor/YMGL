/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

/**
 *
 * @author woderchen
 */
public abstract class UIRunnable<T> implements Runnable {

    T value;

    public UIRunnable() {
        this(null);
    }

    public UIRunnable(T value) {
        this.value = value;
    }

    public T getValue(){
      return value;
    }

    @Override
    public abstract void run();
}
