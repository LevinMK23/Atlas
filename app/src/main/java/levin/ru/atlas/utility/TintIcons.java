package levin.ru.atlas.utility;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

public class TintIcons {

    public static Drawable tintIcon(Drawable icon, ColorStateList colorStateList) {
        if(icon!=null) {
            icon = DrawableCompat.wrap(icon).mutate();
            DrawableCompat.setTintList(icon, colorStateList);
            DrawableCompat.setTintMode(icon, PorterDuff.Mode.SRC_IN);
        }
        return icon;
    }

    public static void tintImageView(ImageView imageView, int colorStateListResId) {
        ColorStateList list = ContextCompat.getColorStateList(imageView.getContext(), colorStateListResId);
        if (list != null) {
            imageView.setImageDrawable(tintIcon(imageView.getDrawable(), list));
        }
    }

    public static int tintIcon(int id, int colorPrimaryDark) {
        return 1;
    }
}

