// IRemoteService.aidl
package com.wisethan.myautomessagetest;

interface IRemoteService {
    int getPid();

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString);
}
