package com.yunzao.project.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ike.l2_base.base.ToolBarFragment;
import com.yunzao.project.R;
import com.yunzao.project.model.bean.DataItem;
import com.yunzao.project.mvp.contract.MainContract;
import com.yunzao.project.mvp.model.MainModelImpl;
import com.yunzao.project.mvp.presenter.MainPresenterImpl;
import com.yunzao.project.ui.activity.WebActivity;
import com.yunzao.project.ui.adapter.DataAdapter;
import com.yunzao.project.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @auth ike
 * @date 2018/8/30
 * @desc 类描述：
 */

public class MainPageFragment extends ToolBarFragment<MainPresenterImpl, MainModelImpl> implements MainContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshView)
    SwipeRefreshLayout swipeRefreshView;

    private String mTitle;
    private int page = 1;
    private ArrayList<DataItem> mDatas;
    private DataAdapter adapter;
    private LinearLayoutManager manager;

    public static MainPageFragment newInstance() {
        MainPageFragment fragment = new MainPageFragment();
        return fragment;
    }

    public MainPageFragment setTitle(String title) {
        mTitle = title;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPresenter.presenterGetData(mTitle, 10, page);
        View view = inflater.inflate(R.layout.fragment_mainpage, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showDatas(ArrayList<DataItem> datas) {
        if (datas.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            if (page == 1) {
                this.mDatas.clear();
            }
            this.mDatas.addAll(datas);
            adapter.notifyDataSetChanged();
        } else {
            recyclerView.setVisibility(View.GONE);
        }
        swipeRefreshView.setRefreshing(false);
    }


    private void init() {
        swipeRefreshView.setOnRefreshListener(this);
        swipeRefreshView.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mDatas = new ArrayList<>();
        adapter = new DataAdapter(getActivity(), mDatas);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisiblePosition = manager.findLastVisibleItemPosition();
                    if (lastVisiblePosition >= manager.getItemCount() - 1) {
                        ++page;
                        mPresenter.presenterGetData(mTitle, 10, page);
                    }
                }
            }
        });

        adapter.setItemClickListener(new DataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra(Constants.TITLE_H5, mDatas.get(position).getDesc());
                intent.putExtra(Constants.URL_H5, mDatas.get(position).getUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.presenterGetData(mTitle, 10, page);
    }
}
