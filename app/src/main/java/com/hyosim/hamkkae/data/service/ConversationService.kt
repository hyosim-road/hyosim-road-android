package com.hyosim.hamkkae.data.service

import com.hyosim.hamkkae.data.request_dto.PostAnswerRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.conversation.GetAnswersResponseData
import com.hyosim.hamkkae.data.response_dto.conversation.GetQuestionResponseData
import com.hyosim.hamkkae.data.response_dto.conversation.PostAnswerResponseData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ConversationService {
    @GET("/conversation/")
    suspend fun getQuestion(
        @Query("tripId") tripId:Int,
    ): ApiResponse<GetQuestionResponseData>

    @POST("/conversation/")
    suspend fun postAnswer(
        @Body postAnswerRequestDto: PostAnswerRequestDto
    ):ApiResponse<PostAnswerResponseData>

    @GET("/conversation/list")
    suspend fun getConversations(
        @Query("tripId") tripId:Int,
    ): ApiResponse<List<GetAnswersResponseData>>
}