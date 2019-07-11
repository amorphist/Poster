package az.amorphist.poster.presentation.post;

import moxy.MvpView;

public interface PostView extends MvpView {
    void getMovie(
            String image,
            String background,
            String title,
            String date,
            float  rate,
            float views,
            String description
    );
}