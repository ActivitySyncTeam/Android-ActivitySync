/*
    Class copied from external library (https://github.com/pedrovgs/Renderers)
    adjusted to Activity-Sync project needs
*/

package com.activity_sync.renderers.base;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic AdapteeCollection implementation based on ArrayList<T>. Library clients can use this
 * class instead of create his own AdapteeCollections.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class ListAdapteeCollection<T> extends ArrayList<T> implements AdapteeCollection<T>
{

    public ListAdapteeCollection()
    {
    }

    public ListAdapteeCollection(List<T> list)
    {
        super(list);
    }
}
