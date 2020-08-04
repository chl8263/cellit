package me.ewan.cellit.domain.channel.vo.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.jpa.repository.Temporal
import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.*

@Entity
class ChannelPost (

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var channelPostId: Long? = null,

        @Column
        var channelPostName: String,

//        @Column
//        var channelPostContent: String,

        @Column
        var accountId: Long,

        @Column
        var accountName: String,

        @Column
        var viewCount: Long = 0,

        @JsonIgnore // prevent infinity loop when trans JSON
        @ManyToOne
        @JoinColumn(name = "channelId")
        var channel: Channel,

        @JsonIgnore // prevent infinity loop when trans JSON
        @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "channelPostContentId", nullable = false)
        var channelPostContent: ChannelPostContent? = null,

        // default fetch type = LAZY
        @JsonIgnore // prevent infinity loop when trans JSON
        @OneToMany(mappedBy = "channelPost", fetch = FetchType.LAZY)
        var channelPostComments: MutableList<ChannelPostComment> = mutableListOf(),

        @Temporal(TemporalType.TIMESTAMP)
        var createDate: String = SimpleDateFormat("yyyy-MM-dd.HH:mm:ss").format(Date()),

        @Temporal(TemporalType.TIMESTAMP)
        var modifyDate: String = SimpleDateFormat("yyyy-MM-dd.HH:mm:ss").format(Date()),

        var active: Int = 1
)