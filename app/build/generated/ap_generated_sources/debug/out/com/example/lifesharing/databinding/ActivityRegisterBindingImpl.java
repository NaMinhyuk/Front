package com.example.lifesharing.databinding;
import com.example.lifesharing.R;
import com.example.lifesharing.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityRegisterBindingImpl extends ActivityRegisterBinding implements com.example.lifesharing.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.textView, 8);
        sViewsWithIds.put(R.id.textView1, 9);
        sViewsWithIds.put(R.id.textView2, 10);
        sViewsWithIds.put(R.id.nickname_text, 11);
        sViewsWithIds.put(R.id.nickname_text1, 12);
        sViewsWithIds.put(R.id.nickname_text2, 13);
        sViewsWithIds.put(R.id.nickname_text3, 14);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final android.widget.Button mboundView7;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener editTextTextEmailAddressandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.email.getValue()
            //         is viewModel.email.setValue((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(editTextTextEmailAddress);
            // localize variables for thread safety
            // viewModel.email.getValue()
            java.lang.String viewModelEmailGetValue = null;
            // viewModel.email
            androidx.lifecycle.MutableLiveData<java.lang.String> viewModelEmail = null;
            // viewModel
            com.example.lifesharing.login.viewModel.RegisterViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.email != null
            boolean viewModelEmailJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelEmail = viewModel.getEmail();

                viewModelEmailJavaLangObjectNull = (viewModelEmail) != (null);
                if (viewModelEmailJavaLangObjectNull) {




                    viewModelEmail.setValue(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener passwordInputandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.password.getValue()
            //         is viewModel.password.setValue((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(passwordInput);
            // localize variables for thread safety
            // viewModel.password != null
            boolean viewModelPasswordJavaLangObjectNull = false;
            // viewModel.password.getValue()
            java.lang.String viewModelPasswordGetValue = null;
            // viewModel
            com.example.lifesharing.login.viewModel.RegisterViewModel viewModel = mViewModel;
            // viewModel.password
            androidx.lifecycle.MutableLiveData<java.lang.String> viewModelPassword = null;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelPassword = viewModel.getPassword();

                viewModelPasswordJavaLangObjectNull = (viewModelPassword) != (null);
                if (viewModelPasswordJavaLangObjectNull) {




                    viewModelPassword.setValue(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener passwordInput1androidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.checkPassword.getValue()
            //         is viewModel.checkPassword.setValue((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(passwordInput1);
            // localize variables for thread safety
            // viewModel.checkPassword
            androidx.lifecycle.MutableLiveData<java.lang.String> viewModelCheckPassword = null;
            // viewModel.checkPassword != null
            boolean viewModelCheckPasswordJavaLangObjectNull = false;
            // viewModel.checkPassword.getValue()
            java.lang.String viewModelCheckPasswordGetValue = null;
            // viewModel
            com.example.lifesharing.login.viewModel.RegisterViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelCheckPassword = viewModel.getCheckPassword();

                viewModelCheckPasswordJavaLangObjectNull = (viewModelCheckPassword) != (null);
                if (viewModelCheckPasswordJavaLangObjectNull) {




                    viewModelCheckPassword.setValue(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener passwordInput2androidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.name.getValue()
            //         is viewModel.name.setValue((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(passwordInput2);
            // localize variables for thread safety
            // viewModel.name != null
            boolean viewModelNameJavaLangObjectNull = false;
            // viewModel.name
            androidx.lifecycle.MutableLiveData<java.lang.String> viewModelName = null;
            // viewModel
            com.example.lifesharing.login.viewModel.RegisterViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.name.getValue()
            java.lang.String viewModelNameGetValue = null;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelName = viewModel.getName();

                viewModelNameJavaLangObjectNull = (viewModelName) != (null);
                if (viewModelNameJavaLangObjectNull) {




                    viewModelName.setValue(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener passwordInput3androidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.phone.getValue()
            //         is viewModel.phone.setValue((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(passwordInput3);
            // localize variables for thread safety
            // viewModel.phone != null
            boolean viewModelPhoneJavaLangObjectNull = false;
            // viewModel
            com.example.lifesharing.login.viewModel.RegisterViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.phone
            androidx.lifecycle.MutableLiveData<java.lang.String> viewModelPhone = null;
            // viewModel.phone.getValue()
            java.lang.String viewModelPhoneGetValue = null;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelPhone = viewModel.getPhone();

                viewModelPhoneJavaLangObjectNull = (viewModelPhone) != (null);
                if (viewModelPhoneJavaLangObjectNull) {




                    viewModelPhone.setValue(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener passwordInput4androidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.verifiedNumber.getValue()
            //         is viewModel.verifiedNumber.setValue((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(passwordInput4);
            // localize variables for thread safety
            // viewModel.verifiedNumber != null
            boolean viewModelVerifiedNumberJavaLangObjectNull = false;
            // viewModel
            com.example.lifesharing.login.viewModel.RegisterViewModel viewModel = mViewModel;
            // viewModel.verifiedNumber
            androidx.lifecycle.MutableLiveData<java.lang.String> viewModelVerifiedNumber = null;
            // viewModel.verifiedNumber.getValue()
            java.lang.String viewModelVerifiedNumberGetValue = null;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelVerifiedNumber = viewModel.getVerifiedNumber();

                viewModelVerifiedNumberJavaLangObjectNull = (viewModelVerifiedNumber) != (null);
                if (viewModelVerifiedNumberJavaLangObjectNull) {




                    viewModelVerifiedNumber.setValue(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };

    public ActivityRegisterBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private ActivityRegisterBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 6
            , (android.widget.EditText) bindings[1]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[14]
            , (android.widget.EditText) bindings[2]
            , (android.widget.EditText) bindings[3]
            , (android.widget.EditText) bindings[4]
            , (android.widget.EditText) bindings[5]
            , (android.widget.EditText) bindings[6]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[10]
            );
        this.editTextTextEmailAddress.setTag(null);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView7 = (android.widget.Button) bindings[7];
        this.mboundView7.setTag(null);
        this.passwordInput.setTag(null);
        this.passwordInput1.setTag(null);
        this.passwordInput2.setTag(null);
        this.passwordInput3.setTag(null);
        this.passwordInput4.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new com.example.lifesharing.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x100L;
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
            setActivity((com.example.lifesharing.login.RegisterActivity) variable);
        }
        else if (BR.viewModel == variableId) {
            setViewModel((com.example.lifesharing.login.viewModel.RegisterViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setActivity(@Nullable com.example.lifesharing.login.RegisterActivity Activity) {
        this.mActivity = Activity;
    }
    public void setViewModel(@Nullable com.example.lifesharing.login.viewModel.RegisterViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x80L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelVerifiedNumber((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 1 :
                return onChangeViewModelEmail((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 2 :
                return onChangeViewModelPhone((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 3 :
                return onChangeViewModelCheckPassword((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 4 :
                return onChangeViewModelName((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 5 :
                return onChangeViewModelPassword((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelVerifiedNumber(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelVerifiedNumber, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelEmail(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelEmail, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelPhone(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelPhone, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelCheckPassword(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelCheckPassword, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelName(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelName, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelPassword(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelPassword, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
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
        java.lang.String viewModelEmailGetValue = null;
        java.lang.String viewModelPasswordGetValue = null;
        java.lang.String viewModelVerifiedNumberGetValue = null;
        java.lang.String viewModelNameGetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelVerifiedNumber = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelEmail = null;
        java.lang.String viewModelCheckPasswordGetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelPhone = null;
        java.lang.String viewModelPhoneGetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelCheckPassword = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelName = null;
        com.example.lifesharing.login.viewModel.RegisterViewModel viewModel = mViewModel;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelPassword = null;

        if ((dirtyFlags & 0x1bfL) != 0) {


            if ((dirtyFlags & 0x181L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.verifiedNumber
                        viewModelVerifiedNumber = viewModel.getVerifiedNumber();
                    }
                    updateLiveDataRegistration(0, viewModelVerifiedNumber);


                    if (viewModelVerifiedNumber != null) {
                        // read viewModel.verifiedNumber.getValue()
                        viewModelVerifiedNumberGetValue = viewModelVerifiedNumber.getValue();
                    }
            }
            if ((dirtyFlags & 0x182L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.email
                        viewModelEmail = viewModel.getEmail();
                    }
                    updateLiveDataRegistration(1, viewModelEmail);


                    if (viewModelEmail != null) {
                        // read viewModel.email.getValue()
                        viewModelEmailGetValue = viewModelEmail.getValue();
                    }
            }
            if ((dirtyFlags & 0x184L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.phone
                        viewModelPhone = viewModel.getPhone();
                    }
                    updateLiveDataRegistration(2, viewModelPhone);


                    if (viewModelPhone != null) {
                        // read viewModel.phone.getValue()
                        viewModelPhoneGetValue = viewModelPhone.getValue();
                    }
            }
            if ((dirtyFlags & 0x188L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.checkPassword
                        viewModelCheckPassword = viewModel.getCheckPassword();
                    }
                    updateLiveDataRegistration(3, viewModelCheckPassword);


                    if (viewModelCheckPassword != null) {
                        // read viewModel.checkPassword.getValue()
                        viewModelCheckPasswordGetValue = viewModelCheckPassword.getValue();
                    }
            }
            if ((dirtyFlags & 0x190L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.name
                        viewModelName = viewModel.getName();
                    }
                    updateLiveDataRegistration(4, viewModelName);


                    if (viewModelName != null) {
                        // read viewModel.name.getValue()
                        viewModelNameGetValue = viewModelName.getValue();
                    }
            }
            if ((dirtyFlags & 0x1a0L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.password
                        viewModelPassword = viewModel.getPassword();
                    }
                    updateLiveDataRegistration(5, viewModelPassword);


                    if (viewModelPassword != null) {
                        // read viewModel.password.getValue()
                        viewModelPasswordGetValue = viewModelPassword.getValue();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x182L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.editTextTextEmailAddress, viewModelEmailGetValue);
        }
        if ((dirtyFlags & 0x100L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.editTextTextEmailAddress, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, editTextTextEmailAddressandroidTextAttrChanged);
            this.mboundView7.setOnClickListener(mCallback1);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.passwordInput, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, passwordInputandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.passwordInput1, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, passwordInput1androidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.passwordInput2, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, passwordInput2androidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.passwordInput3, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, passwordInput3androidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.passwordInput4, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, passwordInput4androidTextAttrChanged);
        }
        if ((dirtyFlags & 0x1a0L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.passwordInput, viewModelPasswordGetValue);
        }
        if ((dirtyFlags & 0x188L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.passwordInput1, viewModelCheckPasswordGetValue);
        }
        if ((dirtyFlags & 0x190L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.passwordInput2, viewModelNameGetValue);
        }
        if ((dirtyFlags & 0x184L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.passwordInput3, viewModelPhoneGetValue);
        }
        if ((dirtyFlags & 0x181L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.passwordInput4, viewModelVerifiedNumberGetValue);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        com.example.lifesharing.login.viewModel.RegisterViewModel viewModel = mViewModel;
        // viewModel != null
        boolean viewModelJavaLangObjectNull = false;



        viewModelJavaLangObjectNull = (viewModel) != (null);
        if (viewModelJavaLangObjectNull) {


            viewModel.registerLogic();
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.verifiedNumber
        flag 1 (0x2L): viewModel.email
        flag 2 (0x3L): viewModel.phone
        flag 3 (0x4L): viewModel.checkPassword
        flag 4 (0x5L): viewModel.name
        flag 5 (0x6L): viewModel.password
        flag 6 (0x7L): activity
        flag 7 (0x8L): viewModel
        flag 8 (0x9L): null
    flag mapping end*/
    //end
}