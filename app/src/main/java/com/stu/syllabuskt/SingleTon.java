package com.stu.syllabuskt;

/**
 * yuan
 * 2020/9/4
 **/
public abstract class SingleTon<T, P> {
    /**
     * 唯一实例
     */
    private volatile T mInstance;

    /**
     * 创建实例
     */
    protected abstract T create(P p);

    /**
     * 获取实例
     */
    public final T get(P p) {
        if (mInstance == null) {
            synchronized (this) {
                if (mInstance == null)
                    mInstance = create(p);
            }
        }
        return mInstance;
    }
}
