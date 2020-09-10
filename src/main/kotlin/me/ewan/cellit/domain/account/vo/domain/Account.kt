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

package me.ewan.cellit.domain.account.vo.domain

import me.ewan.cellit.domain.cell.vo.domain.AccountCell
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.jpa.repository.Temporal
import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.*


@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator::class, property = "accountId")
class Account(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var accountId: Long? = null,

        @Column(unique = true)
        var accountname: String,

        @Column
        var password: String,

        @Column
        @Enumerated(EnumType.STRING)
        var role: AccountRole = AccountRole.ROLE_USER,

        // default fetch type = LAZY
        @JsonIgnore // prevent infinity loop when trans JSON
        @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
        var accountCells: MutableList<AccountCell> = mutableListOf(),

        // default fetch type = LAZY
        @JsonIgnore // prevent infinity loop when trans JSON
        @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
        var accountNotification: MutableList<AccountNotification> = mutableListOf(),

        //@CreationTimestamp
        @Column
        @Temporal(TemporalType.TIMESTAMP)
        var createDate: String = SimpleDateFormat("yyyy-MM-dd.HH:mm:ss").format(Date()),

        //@CreationTimestamp
        @Column
        @Temporal(TemporalType.TIMESTAMP)
        var modifyDate: String = SimpleDateFormat("yyyy-MM-dd.HH:mm:ss").format(Date()),

        var active: Int = 1
)
//{
//        override fun toString() = kotlinToString(properties = toStringProperties)
//
//        override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)
//
//        override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)
//
//
//        companion object {
//                private val equalsAndHashCodeProperties = arrayOf(Account::accountId)
//                private val toStringProperties = arrayOf(
//                        Account::accountId,
//                        Account::accountname
//                )
//        }
//}