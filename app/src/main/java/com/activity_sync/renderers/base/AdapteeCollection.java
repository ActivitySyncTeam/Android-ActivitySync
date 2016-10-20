/*
    Class copied from external library (https://github.com/pedrovgs/Renderers)
    adjusted to Activity-Sync project needs
*/

package com.activity_sync.renderers.base;
import java.util.Collection;

/**
 * Interface created to represent the adaptee collection used in RendererAdapter and
 * RVRendererAdapter. RendererAdapter and RVRendererAdapter will be created with a RendererBuilder
 * and an AdapteeCollection that store all the content to show in a ListView or RecyclerView
 * widget.
 * <p>
 * This library provides a default implementation of AdapteeCollection named ListAdapteeCollection,
 * use it if needed or create your own AdapteeCollection implementations.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public interface AdapteeCollection<T>
{
    /**
     * @return size of the adaptee collection.
     */
    int size();

    /**
     * Search an element using the index passed as argument.
     *
     * @param index to search in the collection.
     * @return the element stored at index passed as argument.
     */
    T get(int index);

    /**
     * Add a new element to the AdapteeCollection.
     *
     * @param element to add.
     */
    boolean add(T element);

    /**
     * Remove one element from the AdapteeCollection.
     */
    boolean remove(Object element);

    /**
     * Add a element collection to the AdapteeCollection.
     *
     * @param elements to add.
     */
    boolean addAll(Collection<? extends T> elements);

    /**
     * Remove a element collection to the AdapteeCollection.
     */
    boolean removeAll(Collection<?> elements);

    /**
     * Remove all element inside the AdapteeCollection.
     */
    void clear();
}