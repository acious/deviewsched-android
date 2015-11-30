package com.gdgssu.android_deviewsched.ui.sche;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gdgssu.android_deviewsched.R;
import com.gdgssu.android_deviewsched.model.FavoriteSession;
import com.gdgssu.android_deviewsched.model.sessioninfo.Track;

import static com.gdgssu.android_deviewsched.util.LogUtils.makeLogTag;

public class SchePagerFragment extends Fragment {

    private static final String TAG = makeLogTag("SchePagerFragment");

    private Track mTrackData;

    private ListView mListview;
    private SchePagerAdapter mAdapter;
    private FavoriteSession mSessionList;

    public static SchePagerFragment newInstance(Track track) {
        SchePagerFragment fragment = new SchePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG, track);
        fragment.setArguments(bundle);


        return fragment;
    }

    public SchePagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mTrackData = (Track) getArguments().getSerializable(TAG);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schepager, container, false);

        initScheListView(rootView);

        return rootView;
    }

    private void initScheListView(View rootView) {
        mListview = (ListView) rootView.findViewById(R.id.schepager_list);
        mAdapter = new SchePagerAdapter(mTrackData, getActivity());

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mTrackData.sessions.get(position).is_session) {
                    ((ScheActivity) getActivity()).setDetailSessionFragment(mTrackData.sessions.get(position));
                }
            }
        });
        mListview.setAdapter(mAdapter);
    }
}
