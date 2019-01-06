package com.videoapp.fagments;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import com.videoapp.activities.DashboardActivity;
import com.videoapp.adapter.MyAdapter;

import com.videoapp.R;
import com.videoapp.model.Itemlistobject;


public class HomeFragment extends Fragment implements MyAdapter.ItemClickListener {
    Button button_f;
    TextView t1,t2,t3;
    private RecyclerView mRecyclerView;
    DashboardActivity activity;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnFragmentInteractionListener mListener;

    Context context;

    public HomeFragment() {
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.activity = (DashboardActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_home, container, false);
        //mRecyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view);

// use this setting to improve performance if you know that changes
// in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);
        List<Itemlistobject> rowListItem = getAllItemList();
// use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        //mRecyclerView.setLayoutManager(mLayoutManager);
// specify an adapter (see also next example)
        mAdapter =new MyAdapter(getActivity(),rowListItem);
        //mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.vp);
        setUpViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.result_tabs);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }
    private List<Itemlistobject> getAllItemList() {

        List<Itemlistobject> allItems = new ArrayList<Itemlistobject>();

        allItems.add(new Itemlistobject("Banwait productions","", R.mipmap.aliya));
        allItems.add(new Itemlistobject("Mohit","insta-royal" + "-gabru",R.drawable.k));
        allItems.add(new Itemlistobject("sonakshi","pls like and comment", R.mipmap.aliya));
        allItems.add(new Itemlistobject("balwant singh","", R.drawable.k));
        allItems.add(new Itemlistobject("Akki sharma","acting good",R.mipmap.aliya));
        allItems.add(new Itemlistobject("Priyanshu","thank u friends",R.drawable.k));
        allItems.add(new Itemlistobject("Your rajni","",R.mipmap.aliya));
        allItems.add(new Itemlistobject("yash","",R.drawable.k));
        allItems.add(new Itemlistobject("Yuvansh","please subcribe my vedio", R.drawable.k));

        return allItems;

    }

    private void setUpViewPager(ViewPager viewPager){

        ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager());
        adapter.addFrag(new RecommendedFragment(), "Recommended");
        adapter.addFrag(new FollowFragment(), "Follow");
        viewPager.setAdapter(adapter);

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<android.support.v4.app.Fragment> mFragmentList = new ArrayList<>();//fragment array list
        private final List<String> mFragmentTitleList = new ArrayList<>();//title array list

        public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
            super(manager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }


        //adding fragments and title method
        public void addFrag(android.support.v4.app.Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void itemClick(View view, int position) {
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
