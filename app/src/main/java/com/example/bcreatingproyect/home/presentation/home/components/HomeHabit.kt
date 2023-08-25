package com.example.bcreatingproyect.home.presentation.home.components

import android.content.DialogInterface.OnClickListener
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bcreatingproyect.core.presentation.HabitCheckbox
import com.example.bcreatingproyect.home.domain.models.Habit
import java.time.LocalDate

@Composable
fun HomeHabit (
    habit: Habit,
    selectedDate:LocalDate,
    onCheckedChangeListener: ()->Unit,
    onClickListener: ()->Unit,
    modifier: Modifier=Modifier

){
    Row (
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
            .background(color = Color.White)
            .clickable {
                onClickListener()
            }
            .padding(19.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text = habit.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        habit.completedDates?.let {
            HabitCheckbox(isChecked =  it.contains(selectedDate)) {
                onCheckedChangeListener()
            }
        }
    }
}