package com.example.lifesharing.databinding;
import com.example.lifesharing.R;
import com.example.lifesharing.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivitySocialLoginBindingImpl extends ActivitySocialLoginBinding implements com.example.lifesharing.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final android.widget.Button mboundView1;
    @NonNull
    private final android.widget.Button mboundView2;
    @NonNull
    private final android.widget.Button mboundView3;
    @NonNull
    private final android.widget.TextView mboundView4;
    @NonNull
    private final android.widget.TextView mboundView5;
    @NonNull
    private final android.widget.TextView mboundView6;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback7;
    @Nullable
    private final android.view.View.OnClickListener mCallback8;
    @Nullable
    private final android.view.View.OnClickListener mCallback5;
    @Nullable
    private final android.view.View.OnClickListener mCallback6;
    @Nullable
    private final android.view.View.OnClickListener mCallback3;
    @Nullable
    private final android.view.View.OnClickListener mCallback4;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivitySocialLoginBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }
    private ActivitySocialLoginBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.Button) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (android.widget.Button) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView3 = (android.widget.Button) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView4 = (android.widget.TextView) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView5 = (android.widget.TextView) bindings[5];
        this.mboundView5.setTag(null);
        this.mboundView6 = (android.widget.TextView) bindings[6];
        this.mboundView6.setTag(null);
        setRootTag(root);
        // listeners
        mCallback7 = new com.example.lifesharing.generated.callback.OnClickListener(this, 5);
        mCallback8 = new com.example.lifesharing.generated.callback.OnClickListener(this, 6);
        mCallback5 = new com.example.lifesharing.generated.callback.OnClickListener(this, 3);
        mCallback6 = new com.example.lifesharing.generated.callback.OnClickListener(this, 4);
        mCallback3 = new com.example.lifesharing.generated.callback.OnClickListener(this, 1);
        mCallback4 = new com.example.lifesharing.generated.callback.OnClickListener(this, 2);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.activity == variableId) {
            setActivity((com.example.lifesharing.login.SocialLoginActivity) variable);
        }
        else if (BR.viewModel == variableId) {
            setViewModel((com.example.lifesharing.login.viewModel.SocialLoginViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setActivity(@Nullable com.example.lifesharing.login.SocialLoginActivity Activity) {
        this.mActivity = Activity;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.activity);
        super.requestRebind();
    }
    public void setViewModel(@Nullable com.example.lifesharing.login.viewModel.SocialLoginViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.example.lifesharing.login.SocialLoginActivity activity = mActivity;
        com.example.lifesharing.login.viewModel.SocialLoginViewModel viewModel = mViewModel;
        // batch finished
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            this.mboundView1.setOnClickListener(mCallback3);
            this.mboundView2.setOnClickListener(mCallback4);
            this.mboundView3.setOnClickListener(mCallback5);
            this.mboundView4.setOnClickListener(mCallback6);
            this.mboundView5.setOnClickListener(mCallback7);
            this.mboundView6.setOnClickListener(mCallback8);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 5: {
                // localize variables for thread safety
                // viewModel
                com.example.lifesharing.login.viewModel.SocialLoginViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    viewModel.resetPassword();
                }
                break;
            }
            case 6: {
                // localize variables for thread safety
                // viewModel
                com.example.lifesharing.login.viewModel.SocialLoginViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    viewModel.loginToEmail();
                }
                break;
            }
            case 3: {
                // localize variables for thread safety
                // viewModel
                com.example.lifesharing.login.viewModel.SocialLoginViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {



                    viewModel.googleLogin(callbackArg_0);
                }
                break;
            }
            case 4: {
                // localize variables for thread safety
                // viewModel
                com.example.lifesharing.login.viewModel.SocialLoginViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    viewModel.registerPage();
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // viewModel
                com.example.lifesharing.login.viewModel.SocialLoginViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    viewModel.kakaoLogin();
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // activity != null
                boolean activityJavaLangObjectNull = false;
                // activity
                com.example.lifesharing.login.SocialLoginActivity activity = mActivity;



                activityJavaLangObjectNull = (activity) != (null);
                if (activityJavaLangObjectNull) {


                    activity.naverLogin();
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): activity
        flag 1 (0x2L): viewModel
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}