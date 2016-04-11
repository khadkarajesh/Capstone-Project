package com.example.rajesh.expensetracker.dagger.component;

import com.example.rajesh.expensetracker.account.edit.AddAccountModel;
import com.example.rajesh.expensetracker.dagger.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface ExpenseTrackerComponent {
    void inject(AddAccountModel addAccountModel);
}
