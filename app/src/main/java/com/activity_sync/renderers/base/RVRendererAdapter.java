/*
    Class copied from external library (https://github.com/pedrovgs/Renderers)
    adjusted to Activity-Sync project needs
*/

package com.activity_sync.renderers.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * RecyclerView.Adapter extension created to work RendererBuilders and Renderer instances. Other
 * adapters have to use this one to show information into RecyclerView widgets.
 * <p>
 * This class is the heart of this library. It's used to avoid the library users declare a new
 * renderer each time they have to show information into a RecyclerView.
 * <p>
 * RVRendererAdapter<T> has to be constructed with a LayoutInflater to inflate views, one
 * RendererBuilder to provide Renderer to RVRendererAdapter and one AdapteeCollection to
 * provide the elements to render.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */

public class RVRendererAdapter<T> extends RecyclerView.Adapter<RendererViewHolder> implements View.OnClickListener
{

    private final LayoutInflater layoutInflater;
    private final RendererBuilder<T> rendererBuilder;
    private final AdapteeCollection<T> collection;
    private PublishSubject<T> itemSelected = PublishSubject.create();

    public RVRendererAdapter(LayoutInflater layoutInflater, RendererBuilder<T> rendererBuilder,
                             AdapteeCollection<T> collection)
    {
        this.layoutInflater = layoutInflater;
        this.rendererBuilder = rendererBuilder;
        this.collection = collection;
    }

    public RVRendererAdapter(Context context, RendererBuilder<T> rendererBuilder)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.rendererBuilder = rendererBuilder;
        this.collection = new ListAdapteeCollection<>(new ArrayList());
    }


    @Override
    public int getItemCount()
    {
        return collection.size();
    }

    public T getItem(int position)
    {
        return collection.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    /**
     * Indicate to the RecyclerView the type of Renderer used to one position using a numeric value.
     *
     * @param position to analyze.
     * @return the id associated to the Renderer used to render the content given a position.
     */
    @Override
    public int getItemViewType(int position)
    {
        T content = getItem(position);
        return rendererBuilder.getItemViewType(content);
    }

    /**
     * One of the two main methods in this class. Creates a RendererViewHolder instance with a
     * Renderer inside ready to be used. The RendererBuilder to create a RendererViewHolder using the
     * information given as parameter.
     *
     * @param viewGroup used to create the ViewHolder.
     * @param viewType  associated to the renderer.
     * @return ViewHolder extension with the Renderer it has to use inside.
     */
    @Override
    public RendererViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        rendererBuilder.withParent(viewGroup);
        rendererBuilder.withLayoutInflater(layoutInflater);
        rendererBuilder.withViewType(viewType);
        RendererViewHolder viewHolder = rendererBuilder.buildRendererViewHolder();
        if (viewHolder == null)
        {
            throw new RuntimeException("RendererBuilder have to return a not null viewHolder");
        }
        //region fragment of code added to library for Activity-Sync needs
        viewHolder.itemView.setOnClickListener(this);
        //endregion
        return viewHolder;
    }

    /**
     * Given a RendererViewHolder passed as argument and a position renders the view using the
     * Renderer previously stored into the RendererViewHolder.
     *
     * @param viewHolder with a Renderer class inside.
     * @param position   to render.
     */
    @Override
    public void onBindViewHolder(RendererViewHolder viewHolder, int position)
    {
        T content = getItem(position);
        Renderer<T> renderer = viewHolder.getRenderer();
        if (renderer == null)
        {
            throw new RuntimeException("RendererBuilder have to return a not null renderer");
        }
        renderer.setContent(content);
        updateRendererExtraValues(content, renderer, position);
        viewHolder.itemView.setTag(content);
        renderer.render();
    }

    /**
     * Add an element to the AdapteeCollection<T>.
     *
     * @param element to add.
     */
    public void add(T element)
    {
        collection.add(element);
    }

    /**
     * Remove an element from the AdapteeCollection<T>.
     *
     * @param element to remove.
     */
    public void remove(Object element)
    {
        collection.remove(element);
    }

    /**
     * Add a Collection<T> of elements to the AdapteeCollection.
     *
     * @param elements to add.
     */
    public void addAll(Collection<? extends T> elements)
    {
        collection.addAll(elements);
    }

    /**
     * Remove a Collection<T> of elements to the AdapteeCollection.
     *
     * @param elements to remove.
     */
    public void removeAll(Collection<?> elements)
    {
        collection.removeAll(elements);
    }

    /**
     * Remove all elements inside the AdapteeCollection.
     */
    public void clear()
    {
        collection.clear();
    }

    /**
     * Allows the client code to access the AdapteeCollection<T> from subtypes of RendererAdapter.
     *
     * @return collection used in the adapter as the adaptee class.
     */
    protected AdapteeCollection<T> getCollection()
    {
        return collection;
    }

    /**
     * Empty implementation created to allow the client code to extend this class without override
     * getView method.
     * <p>
     * This method is called before render the Renderer and can be used in RendererAdapter extension
     * to add extra info to the renderer created like the position in the ListView/RecyclerView.
     *
     * @param content  to be rendered.
     * @param renderer to be used to paint the content.
     * @param position of the content.
     */
    protected void updateRendererExtraValues(T content, Renderer<T> renderer, int position)
    {

    }

    //region fragment of code added to library for Activity-Sync needs
    @Override
    public void onClick(View v)
    {
        T model = (T) v.getTag();
        this.itemSelected.onNext(model);
    }

    public Observable<T> itemSelected()
    {
        return itemSelected;
    }
    //endregion
}