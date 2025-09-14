package com.hyosim.hamkkae.data.datasource

import com.hyosim.hamkkae.data.request_dto.PostAnswerRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.conversation.GetAnswersResponseData
import com.hyosim.hamkkae.data.response_dto.conversation.GetQuestionResponseData
import com.hyosim.hamkkae.data.response_dto.conversation.PostAnswerResponseData

interface ConversationDataSource {
    suspend fun getQuestion(tripId:Int): ApiResponse<GetQuestionResponseData>
    suspend fun postAnswer(postAnswerRequestDto: PostAnswerRequestDto): ApiResponse<PostAnswerResponseData>
    suspend fun getConversations(tripId:Int): ApiResponse<List<GetAnswersResponseData>>
    suspend fun getCount(tripId: Int): ApiResponse<Int>
}