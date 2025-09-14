package com.hyosim.hamkkae.domain.repository

import com.hyosim.hamkkae.data.request_dto.PostAnswerRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.conversation.GetAnswersResponseData
import com.hyosim.hamkkae.data.response_dto.conversation.GetQuestionResponseData
import com.hyosim.hamkkae.data.response_dto.conversation.PostAnswerResponseData

interface ConversationRepository {
    suspend fun getQuestion(tripId:Int):Result<ApiResponse<GetQuestionResponseData>>
    suspend fun postAnswer(postAnswerRequestDto: PostAnswerRequestDto): Result<ApiResponse<PostAnswerResponseData>>
    suspend fun getConversations(tripId: Int):Result<ApiResponse<List<GetAnswersResponseData>>>
    suspend fun getCount(tripId: Int):Result<ApiResponse<Int>>
}