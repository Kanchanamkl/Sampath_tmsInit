package com.epic.pos.ui.home.banner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.epic.pos.R;
import com.epic.pos.dagger.AppComponent;
import com.epic.pos.databinding.FragmentHomeBannerBinding;
import com.epic.pos.ui.BaseFragment;

public class HomeBannerFragment extends BaseFragment<HomeBannerPresenter> implements HomeBannerContract.View {

    private FragmentHomeBannerBinding binding;
    private static final String ARG_PARAM1 = "param1";

    public static HomeBannerFragment newInstance(int bannerResId) {
        HomeBannerFragment fragment = new HomeBannerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, bannerResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPresenter.bannerResId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_banner, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.ivBanner.setImageResource(mPresenter.bannerResId);
    }

    @Override
    protected void initDependencies(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    public void onDestroyView() {
        //Unbind DataBinding
        if (binding != null) {
            binding.unbind();
            binding = null;
        }

        super.onDestroyView();

    }

}