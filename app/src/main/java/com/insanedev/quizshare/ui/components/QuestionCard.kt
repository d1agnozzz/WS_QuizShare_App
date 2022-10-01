package com.insanedev.quizshare.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.insanedev.quizshare.network.models.AnswerRespondRemote
import com.insanedev.quizshare.network.models.QuestionType
import com.insanedev.quizshare.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionCard(
    questionNumber: Int,
    questionTitle: String,
    questionType: QuestionType,

    onQuestionTitleChanged: (questionNumber: Int, value: String) -> Unit,
    answers: List<AnswerRespondRemote>,
    onAnswerOptionClicked: (questionNumber:Int, answerId:Int) -> Unit,
    onAnswerContentChanged: (questionNumber:Int, answerId:Int, value:String) -> Unit,
    onAddAnswerClicked: (questionNumber: Int) -> Unit,
    onRemoveQuestionClicked: (questionNumber: Int) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .border(
                1.dp, AppTheme.colors.secondaryText,
                RoundedCornerShape(8.dp)
            ),
//                    shadowElevation = 8.dp,

    ) {
        Box(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                .fillMaxSize()
        )
        {
            Row() {
                // Number
//                Text(
//                    text = "$questionNumber.",
//                    fontSize = 14.sp,
//                    modifier = Modifier.padding(top = 0.dp, end = 8.dp)
//                )
                Column() {
                    // Title

                    QTextField(
                        value = questionTitle,
                        label = "Вопрос $questionNumber",
                        onTextFieldChange = {
                            onQuestionTitleChanged(questionNumber, it)

                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Placeholder Answer 1
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        RadioButton(selected = true, onClick = { /*TODO*/ })
//                        //
//                        TextField(
//                            value = "Ответ placeholder",
//                            onValueChange = {},
//                            colors = TextFieldDefaults.textFieldColors(
//                                containerColor = Color.Transparent
//                            )
//                        )
//                    }

                    answers.forEach { answer ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            when (questionType) {
                                QuestionType.SingleChoice -> {
                                    RadioButton(
                                        selected = answer.correct ?: false,
                                        onClick = { onAnswerOptionClicked(questionNumber, answer.id) })
                                }
                                QuestionType.MultipleChoice -> {
                                    Checkbox(
                                        checked = answer.correct ?: false,
                                        onCheckedChange = { onAnswerOptionClicked(questionNumber, answer.id) })
                                }
                                QuestionType.Input -> {

                                }

                            }
                            TextField(
                                value = answer.content,
                                onValueChange = {
                                    onAnswerContentChanged(questionNumber, answer.id, it)
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.Transparent
                                ),
                                modifier = Modifier.fillMaxWidth()

                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    // Bottom Buttons
                    Row(
                        verticalAlignment = Alignment.CenterVertically,

                    ) {
                        // Add Question Button
                        if (questionType != QuestionType.Input) {
                            FilledTonalButton(
                                onClick = { onAddAnswerClicked(questionNumber) },
                                modifier = Modifier.width(80.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AppTheme.colors.primaryAccent
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Add,
                                    contentDescription = "Add Question",
                                    tint = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            modifier = Modifier.clickable(onClick = {
                                onRemoveQuestionClicked(
                                    questionNumber
                                )
                            })
                        )
                    }
                }
            }
        }
    }
}
