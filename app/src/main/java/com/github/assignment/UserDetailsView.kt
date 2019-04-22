package com.github.assignment

import com.github.assignment.network.responses.UserDetails

/**
 * @author chenchris on 2019/4/24.
 */
interface UserDetailsView : BaseView {
    fun onFetchUserDetails(userDetails: UserDetails)
}