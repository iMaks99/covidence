package com.sr.covidence.models.dto

import com.sr.covidence.models.MessageInterface

data class UserMessageDto(
    var messageText: String
) : MessageInterface