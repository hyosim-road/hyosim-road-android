package com.hyosim.hamkkae.data.datasource_impl

import com.hyosim.hamkkae.data.datasource.ConversationDataSource
import com.hyosim.hamkkae.data.request_dto.PostAnswerRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.conversation.GetQuestionResponseData
import com.hyosim.hamkkae.data.service.ConversationService
import javax.inject.Inject

class ConversationDataSourceImpl @Inject constructor(
    private val conversationService: ConversationService
): ConversationDataSource {
    override suspend fun getQuestion(tripId: Int): ApiResponse<GetQuestionResponseData> = conversationService.getQuestion(tripId)
    override suspend fun postAnswer(postAnswerRequestDto: PostAnswerRequestDto): ApiResponse<Unit> = conversationService.postAnswer(postAnswerRequestDto)
    override suspend fun getConversations(tripId: Int): ApiResponse<Unit> = conversationService.getConversations(tripId)
}