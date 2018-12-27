package my.edu.tarc.lab42database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.os.AsyncTask;
import java.util.List;

//TODO 6: Create a repository class to manage data query thread

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        //get a copy of database
        AppDatabase db = AppDatabase.getDatabase(application);

        //Associate database to DAO
        userDao = db.userDao();

        //Retrieve all the user records
        allUsers = userDao.loadAllUsers();
    }
    
    LiveData<List<User>> getAllUsers(){
        return allUsers;
    }

    public void  deleteUser(User user){ new deleteAsyncTask(userDao).execute(user); }
    public void insertUser(User user){
        new insertAsyncTask(userDao).execute(user);
    }

    //<Param, Progress, Results>
    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public insertAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        // '...' is an array
        @Override

        protected Void doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public deleteAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        // '...' is an array
        @Override

        protected Void doInBackground(User... users) {
            userDao.deleteUser(users[0]);
            return null;
        }
    }

}
