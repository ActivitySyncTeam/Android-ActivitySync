/*
    Class copied from external library (https://github.com/pedrovgs/Renderers)
    adjusted to Activity-Sync project needs
*/

package com.activity_sync.renderers.base;

import android.support.v7.widget.RecyclerView;

/**
 * RecyclerView.ViewHolder extension created to be able to use Renderer classes in RecyclerView
 * widgets. This class will be completely hidden to the library clients.
 */
public class RendererViewHolder extends RecyclerView.ViewHolder
{
    private final Renderer renderer;

    public RendererViewHolder(Renderer renderer)
    {
        super(renderer.getRootView());
        this.renderer = renderer;
    }

    public Renderer getRenderer()
    {
        return renderer;
    }
}
