package com.hyosim.hamkkae.data.repository_impl

import com.hyosim.hamkkae.data.datasource.ConversationDataSource
import com.hyosim.hamkkae.data.request_dto.PostAnswerRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.conversation.GetQuestionResponseData
import com.hyosim.hamkkae.domain.repository.ConversationRepository
import timber.log.Timber
import javax.inject.Inject

class ConversationRepositoryImpl @Inject constructor(
    private val conversationDataSource: ConversationDataSource
): ConversationRepository {
    override suspend fun getQuestion(tripId: Int): Result<ApiResponse<GetQuestionResponseData>> {
        return runCatching {
            conversationDataSource.getQuestion(tripId)
        }.onFailure {
            Timber.e("conversation repository get question fail: $it")
        }
    }

    override suspend fun postAnswer(postAnswerRequestDto: PostAnswerRequestDto): Result<ApiResponse<Unit>> {
        return runCatching {
            conversationDataSource.postAnswer(postAnswerRequestDto)
        }.onFailure {
            Timber.e("conversation repository post answer fail: $it")
        }
    }

    override suspend fun getConversations(tripId: Int): Result<ApiResponse<Unit>> {
        return runCatching {
            conversationDataSource.getConversations(tripId)
        }.onFailure {
            Timber.e("conversation repository get conversations fail: $it")
        }
    }
}