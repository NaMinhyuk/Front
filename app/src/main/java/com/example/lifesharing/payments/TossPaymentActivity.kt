package com.example.lifesharing.payments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lifesharing.BuildConfig
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityTossPaymentsBinding
import com.tosspayments.paymentsdk.PaymentWidget
import com.tosspayments.paymentsdk.model.PaymentCallback
import com.tosspayments.paymentsdk.model.PaymentWidgetOptions
import com.tosspayments.paymentsdk.model.PaymentWidgetStatusListener
import com.tosspayments.paymentsdk.model.TossPaymentResult
import com.tosspayments.paymentsdk.view.PaymentMethod

class TossPaymentsActivity : AppCompatActivity() {

    val TAG: String = "로그"
    val CLIENT_KEY = BuildConfig.TOSS_CLIENT_KEY
    val CUSTOMER_KEY = BuildConfig.TOSS_CUSTOMER_KEY

    private lateinit var binding: ActivityTossPaymentsBinding

    private lateinit var paymentWidget: PaymentWidget


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTossPaymentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.paymentsBackPage.setOnClickListener {
            finish()
        }

        paymentWidget = PaymentWidget(
            activity = this@TossPaymentsActivity,
            clientKey = "CLIENT_KEY",
            customerKey = "7CP_K-CUSTOMER_KEY",
        )

        val paymentMethodWidgetStatusListener = object : PaymentWidgetStatusListener {
            override fun onFail(fail: TossPaymentResult.Fail) {
                Log.e(TAG, "결제 실패: ${fail.errorMessage}")
                // 필요한 경우 사용자에게 실패 사실을 알림
                //Toast.makeText(this@TossPaymentsActivity, "결제에 실패하였습니다: ${fail.errorMessage}", Toast.LENGTH_LONG).show()

            }

            override fun onLoad() {
                val message: String = "결제위젯 렌더링 완료"
                Log.d(TAG, "PaymentsWidgetStatus: $message ")
            }
        }

        paymentWidget.run {
            renderPaymentMethods(
                method = binding.paymentMethodWidget,
                amount = PaymentMethod.Rendering.Amount(10000),
                paymentWidgetStatusListener = paymentMethodWidgetStatusListener
            )
        }

        binding.requestPaymentCta.setOnClickListener{

            if (binding.paymentsPointEditText.toString() != "") {
                binding.requestPaymentCta.setBackgroundColor(0x1277ED)
                binding.requestPaymentCta.setTextColor(0xFFF)
            }

            paymentWidget.requestPayment(
                paymentInfo = PaymentMethod.PaymentInfo(orderId = "asdfadsfadsfaad", orderName = "orderName"),
                paymentCallback = object : PaymentCallback {
                    override fun onPaymentSuccess(success: TossPaymentResult.Success) {
                        Log.d(TAG, "onPaymentSuccess: ${success.paymentKey}")
                        Log.d(TAG, "onPaymentSuccess: ${success.orderId}")
                        Log.d(TAG, "onPaymentSuccess: ${success.amount.toString()}")

                    }

                    override fun onPaymentFailed(fail: TossPaymentResult.Fail) {
                        Log.e(TAG, "onPaymentFailed: ${fail.errorMessage}", )
                    }
                }
            )
        }
    }
}