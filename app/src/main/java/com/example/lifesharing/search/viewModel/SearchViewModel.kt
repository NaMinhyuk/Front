package com.example.lifesharing.search.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.search.model.response_body.SearchResult
import com.example.lifesharing.search.work.SearchWork

/** 검색 결과 조회 ViewModel
 * AndroidViewModel을 상속받아 애플리케이션 컨텍스트를 사용
 */
class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val searchWork = SearchWork()   // Model과 통신을 위한 객체
    val searchResults = MutableLiveData<List<SearchResult>>()   // 검색 결과를 저장하는 LiveData 객체
    val errorMessage = MutableLiveData<String>()                // 에러 발생 시 상태를 저장하는 LiveData 객체

    fun getSearchProduct(keyword: String, filter: String){
        // SearchWork의 getSearchProduct 메서드를 호출, 콜백을 통해 결과를 받음
        searchWork.getSearchProduct(keyword, filter) { results, error ->
            if (error != null) {
                // 오류가 있을 경우
                errorMessage.postValue(error)
                Log.e("SearchViewModel", error)
            } else {
                // 결과를 LiveData에 포스트 -> UI가 자동으로 업데이트
                searchResults.postValue(results)
            }
        }
    }
}