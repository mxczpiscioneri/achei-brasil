package br.com.mxczpiscioneri.acheibrasil;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Matheus on 05/05/2016.
 */
public class AndroidImageAdapter extends PagerAdapter {
    Context mContext;
    String[] images;
    LayoutInflater inflater;

    public AndroidImageAdapter(Context mContext, String[] images) {
        this.mContext = mContext;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container, false);

        ImageLoader imageLoader = AppVolleyController.getInstance().getImageLoader();

        NetworkImageView imgEstabelecimento = (NetworkImageView) itemView.findViewById(R.id.imagem_estabelecimento);
        imgEstabelecimento.setImageUrl(images[position], imageLoader);

        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}