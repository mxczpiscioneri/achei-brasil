package br.com.mxczpiscioneri.acheibrasil;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Matheus on 05/05/2016.
 */
public class SorteioImageAdapter extends PagerAdapter {
    Context mContext;

    SorteioImageAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == ((NetworkImageView) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        int resId = 0;
        switch (position) {
            case 0:
                resId = R.id.foto1;
                break;
            case 1:
                resId = R.id.foto2;
                break;
        }
        return container.findViewById(resId);
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        ((ViewPager) container).removeView((ImageView) obj);
    }
}