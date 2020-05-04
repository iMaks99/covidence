package com.sr.covidence.models.model

import com.sr.covidence.models.MessageInterface
import com.sr.covidence.models.QuestionAnswer

data class BotCommandMessage(
    var messageText: String
) : MessageInterface, QuestionAnswer(false)