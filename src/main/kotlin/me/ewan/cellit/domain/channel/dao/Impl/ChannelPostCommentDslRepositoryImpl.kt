/********************************************************************************************
 * Copyright (c) 2020 Ewan Choi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************************/

package me.ewan.cellit.domain.channel.dao.Impl

import me.ewan.cellit.domain.channel.dao.ChannelPostCommentDslRepository
import me.ewan.cellit.domain.channel.vo.domain.ChannelPostComment
import me.ewan.cellit.domain.channel.vo.domain.QChannelPost
import me.ewan.cellit.domain.channel.vo.domain.QChannelPostComment
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

/**
 * @author Ewan
 */
class ChannelPostCommentDslRepositoryImpl : QuerydslRepositorySupport(ChannelPostComment::class.java), ChannelPostCommentDslRepository {

    /**
     * Get ChannelPostComment By ChannelPostId.
     *
     * @author Ewan
     * @param channelPostId
     * @return
     */
    override fun findByChannelPostId(channelPostId: Long): List<ChannelPostComment> {
        val channelPost = QChannelPost.channelPost
        val channelPostComment = QChannelPostComment.channelPostComment1

        return from(channelPostComment)
                .innerJoin(channelPostComment.channelPost, channelPost).fetchJoin()
                .where(channelPostComment.channelPost.channelPostId.eq(channelPostId))
                .fetch()
    }
}