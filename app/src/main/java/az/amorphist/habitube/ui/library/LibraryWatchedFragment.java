package az.amorphist.habitube.ui.library;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import az.amorphist.habitube.R;
import az.amorphist.habitube.adapters.LibraryAdapter;
import az.amorphist.habitube.entities.movie.Movie;
import az.amorphist.habitube.presentation.library.LibraryWatchedPresenter;
import az.amorphist.habitube.presentation.library.LibraryWatchedView;
import az.amorphist.habitube.utils.animation.VegaXLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import toothpick.Toothpick;

import static az.amorphist.habitube.Constants.DI.APP_SCOPE;

public class LibraryWatchedFragment extends MvpAppCompatFragment implements LibraryWatchedView {

    @InjectPresenter LibraryWatchedPresenter watchedPresenter;

    @BindView(R.id.recycler_view_watched) RecyclerView recyclerViewWatched;
    @BindView(R.id.empty_screen) View emptyScreen;
    private LibraryAdapter libraryAdapter;
    private Unbinder unbinder;

    @ProvidePresenter
    LibraryWatchedPresenter watchedPresenter() {
        return Toothpick.openScope(APP_SCOPE).getInstance(LibraryWatchedPresenter.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(APP_SCOPE));
        libraryAdapter = new LibraryAdapter(postId -> Log.d("LIBRARY ID", String.valueOf(postId)));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_library_watched, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerViewWatched.setLayoutManager(new VegaXLayoutManager());
        recyclerViewWatched.setItemAnimator(new DefaultItemAnimator());
        recyclerViewWatched.setHasFixedSize(true);
        recyclerViewWatched.setAdapter(libraryAdapter);
        watchedPresenter.getMovies();
    }

    @Override
    public void showWatchedMovies(List<Movie> movies) {
        Collections.sort(movies, (o1, o2) -> o2.getAddedDate().compareTo(o1.getAddedDate()));
        libraryAdapter.addAllMovies(movies);
        screenWatcher();
    }

    private void screenWatcher() {
        if (libraryAdapter.getItemCount() != 0) {
            emptyScreen.setVisibility(View.GONE);
            recyclerViewWatched.setVisibility(View.VISIBLE);
        } else {
            emptyScreen.setVisibility(View.VISIBLE);
            recyclerViewWatched.setVisibility(View.GONE);
        }
    }
    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}