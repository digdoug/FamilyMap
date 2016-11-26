package com.example.lp1.familymap;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lp1.familymap.model.Login;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements Button.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView userNameTextView;
    private TextView passwordTextView;
    private TextView serverHostTextView;
    private TextView serverPortTextView;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText serverHostEditText;
    private EditText serverPortEditText;
    private Button loginButton;
    private Login loginModel;
    private DownloadTask downloadTask;
    private Boolean canUseMap = false;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // make new Login model
        loginModel = Login.getInstance();
        //DownloadTask.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        userNameTextView = (TextView) v.findViewById(R.id.textView);
        passwordTextView = (TextView) v.findViewById(R.id.textView2);
        serverHostTextView = (TextView) v.findViewById(R.id.textView3);
        serverPortTextView = (TextView) v.findViewById(R.id.textView4);

        userNameEditText = (EditText) v.findViewById(R.id.editText);
        passwordEditText = (EditText) v.findViewById(R.id.editText2);
        serverHostEditText = (EditText) v.findViewById(R.id.editText3);
        serverPortEditText = (EditText) v.findViewById(R.id.editText4);


        // set up button click listener
        // get button listener
        loginButton = (Button) v.findViewById(R.id.button);
        loginButton.setOnClickListener(this);

        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        loginModel.setUserName(userNameEditText.getText().toString());
        loginModel.setPassword(passwordEditText.getText().toString());
        loginModel.setServerHost(serverHostEditText.getText().toString());
        loginModel.setServerPort(serverPortEditText.getText().toString());

        //mListener.onFragmentInteraction(null);
        try {
            URL url = new URL("http://" +serverHostEditText.getText().toString()+":"+
                    serverPortEditText.getText().toString()+"/user/login");
            loginModel.setPostData("{username:\"" + userNameEditText.getText().toString()
                            + "\",password:\"" + passwordEditText.getText().toString()+
                        "\"}");
            downloadTask = new DownloadTask();
            downloadTask.execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public Boolean getCanUseMap() {
        return canUseMap;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        //
        void onFragmentInteraction(Uri uri);
    }

    public class DownloadTask extends AsyncTask<URL,Integer,Long>{

        //private TextView totalSizeTextView;
        //private Button downloadButton;

        public DownloadTask(){}

        @Override
        protected Long doInBackground(URL... params) {
            HttpClient httpClient = new HttpClient();

            long totalSize = 0;

            for (int i = 0; i < params.length; i++) {

                String paramsContent = httpClient.getURL(params[i]);
                if (paramsContent != null) {
                    totalSize += paramsContent.length();
                }
                /*int progress = 0;
                if (i == params.length - 1) {
                    progress = 100;
                } else {
                    float cur = i + 1;
                    float total = params.length;
                    progress = (int) ((cur / total) * 100);
                }
                publishProgress(progress);*/
            }
            //Context context = getApplicationContext();

            return totalSize;
        }

        @Override
        protected void onPostExecute(Long result){


            CharSequence text = "Nice dude";


            int duration = Toast.LENGTH_SHORT;


            loginModel.setCanUseMap(true);
            loginModel.setDidLoginIn(true);

            if (loginModel.getAllEvents() != null &&
                    loginModel.getAllPeople() != null){
                Toast toast = Toast.makeText(getActivity(), text, duration);
                toast.show();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.changetoMap();
            }
            else {
                text = "Error dude ";
                Toast toast = Toast.makeText(getActivity(), text, duration);
                toast.show();
            }


        }



    }


}
