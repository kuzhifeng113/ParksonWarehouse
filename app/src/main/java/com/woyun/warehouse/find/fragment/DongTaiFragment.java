package com.woyun.warehouse.find.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.baseparson.BaseFragment;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 动态
 */
public class DongTaiFragment extends BaseFragment {
    private static final String TAG = "DongTaiFragment";
    private static final String FIND_TYPE ="find_type" ;
    @BindView(R.id.tv)
    TextView tv;
    Unbinder unbinder;

    private String userId;
    private int findTypeId;

    public static DongTaiFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(FIND_TYPE, page);
        DongTaiFragment fragment = new DongTaiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findTypeId = getArguments().getInt(FIND_TYPE);
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_dongt, container, false);

        userId = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, "");
        unbinder = ButterKnife.bind(this, view);
        tv.setText("动态"+findTypeId);
        LogUtils.e(TAG, "onCreateView: "+findTypeId );
        return view;
    }


    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
