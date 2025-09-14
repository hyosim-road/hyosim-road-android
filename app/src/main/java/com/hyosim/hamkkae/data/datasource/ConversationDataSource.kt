package com.hyosim.hamkkae.data.datasource

import com.hyosim.hamkkae.data.request_dto.PostAnswerRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.conversation.GetQuestionResponseData

interface ConversationDataSource {
    suspend fun getQuestion(tripId:Int): ApiResponse<GetQuestionResponseData>
    suspend fun postAnswer(postAnswerRequestDto: PostAnswerRequestDto): ApiResponse<Unit>
    suspend fun getConversations(tripId:Int): ApiResponse<Unit>
}