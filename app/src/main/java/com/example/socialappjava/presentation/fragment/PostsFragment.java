package com.example.socialappjava.presentation.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialappjava.R;
import com.example.socialappjava.data.model.post.APIResponse;
import com.example.socialappjava.databinding.PostsBinding;
import com.example.socialappjava.presentation.adapter.MilestoneAdapter;
import com.example.socialappjava.presentation.adapter.PostAdapter;
import com.example.socialappjava.presentation.viewmodel.ViewModel;
import com.example.socialappjava.presentation.viewmodel.ViewModelFactory;

public class PostsFragment extends Fragment {
    PostsBinding binding;
    PostAdapter postAdapter;
  //  MilestoneAdapter milestoneAdapter;
    ViewModel viewModel;
    ViewModelFactory factory;
    int page = 1;
    int limit = 20;
    int pages = 0;
    boolean isScrolling = false;
    boolean isScrollLoading = false;
    boolean isLoading = false;
    boolean isLastPage = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PostsBinding.inflate(inflater, container, false);

        factory=new ViewModelFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(requireActivity(),factory).get(ViewModel.class);
        postAdapter = new PostAdapter();
      //  milestoneAdapter=new MilestoneAdapter();

        initRecyclerView();
      //  initMilestoneRecyclerview();
        binding.startBtn.setOnClickListener(view -> {
            viewPostList();
        });
        binding.stopBtn.setOnClickListener(view -> {
            viewModel.stopApiCall();
        });
        binding.clearBtn.setOnClickListener(view -> {
            postAdapter.clearList();
        });
       /* binding.pullToRefresh.setColorSchemeColors(
                getResources().getColor(
                        R.color.green,
                        getContext().getTheme()
                )
        );
        binding.pullToRefresh.setOnRefreshListener(() -> binding.pullToRefresh.setRefreshing(false));

*/
        return binding.getRoot();
    }

  /*  private void initMilestoneRecyclerview() {
        binding.milestoneRecyclerview.setAdapter(milestoneAdapter);

    }*/

    private void initRecyclerView() {

        binding.recyclerview.setAdapter(postAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                 //   hideScrollProgressBar();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) binding.recyclerview.getLayoutManager();
                int sizeOfCurrentList = layoutManager.getItemCount();
                int visibleItems = layoutManager.getItemCount();
                int topPosition = layoutManager.findFirstVisibleItemPosition();


                boolean hasReachedToEnd = topPosition + visibleItems >= sizeOfCurrentList;
                boolean shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling;

                if (shouldPaginate) {
                    page++;
                   // showScrollProgressBar();

                    viewModel.getPostsList(page, limit);

                    isScrolling = false;
                }
            }
        });
    }

    private void viewPostList() {
       // showProgressBar();
        viewModel.getPostsList(page,limit);
        viewModel.getPostsMutableLiveData().observe(getViewLifecycleOwner(), new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                Log.d("Users", "SUCCESS");

              //  hideProgressBar();
                if(apiResponse.getData()!=null){
                    postAdapter.setList(apiResponse.getData());
                    if (apiResponse.getTotal() % limit == 0) {
                        pages = apiResponse.getTotal() / limit;
                    } else {
                        pages = apiResponse.getTotal() / limit + 1;
                    }
                    isLastPage = page == pages;
                   // hideScrollProgressBar();
                }

            }
        });

       /* viewModel.getPosts(page, limit);
        viewModel.posts().observe(getViewLifecycleOwner(), APIResponseResource -> {
            switch (APIResponseResource.status) {
                case SUCCESS: {
                    hideProgressBar();
                    Log.d("Users", "SUCCESS");
                    if (APIResponseResource.data != null) {
                        APIResponse response = APIResponseResource.data;
                        postAdapter.setList(response.getData());
                        if (response.getTotal() % limit == 0) {
                            pages = response.getTotal() / limit;
                        } else {
                            pages = response.getTotal() / limit + 1;
                        }
                        isLastPage = page == pages;
                        hideScrollProgressBar();
                    }
                }
                case ERROR: {
                    hideProgressBar();
                    Log.d("Users", "ERROR");

                }
                case LOADING: {
                    Log.d("Users", "LOADING");
                    showProgressBar();
                }
            }
        });*/

    }

    private void showScrollProgressBar() {
        isScrollLoading = true;
        binding.scrollProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideScrollProgressBar() {
        isScrollLoading = false;
        binding.scrollProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        isLoading = true;
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        isLoading = false;
        binding.progressBar.setVisibility(View.INVISIBLE);
    }
}
