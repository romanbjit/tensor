package com.dreampany.frame.ui.model;

import com.dreampany.frame.data.model.Base;
import com.dreampany.frame.ui.adapter.SmartAdapter;
import com.google.common.base.Objects;

import java.io.Serializable;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFilterable;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * Created by Hawladar Roman on 30/4/18.
 * Dreampany
 * dreampanymail@gmail.com
 */
public abstract class BaseItem<T extends Base, VH extends SmartAdapter.SmartViewHolder> extends AbstractFlexibleItem<VH> implements IFlexible<VH>, IFilterable, Serializable {

    protected T item;
    protected int layoutId;

    protected BaseItem(T item, int layoutId) {
        this.item = item;
        this.layoutId = layoutId;
    }

    @Override
    public boolean equals(Object inObject) {
        if (this == inObject) return true;
        if (inObject == null || getClass() != inObject.getClass()) return false;
        BaseItem item = (BaseItem) inObject;
        return Objects.equal(item, item.item);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(item);
    }

    @Override
    public int getLayoutRes() {
        return layoutId;
    }

    public T getItem() {
        return item;
    }
}
