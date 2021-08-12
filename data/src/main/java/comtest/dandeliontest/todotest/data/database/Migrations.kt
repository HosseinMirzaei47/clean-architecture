package comtest.dandeliontest.todotest.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {

        /* Replace "TaskTagCrossRef" table with "task_category_cross_ref" */
        database.execSQL("CREATE TABLE `task_category_cross_ref` (`taskId` INTEGER NOT NULL, `categoryId` INTEGER NOT NULL, PRIMARY KEY(`taskId`, `categoryId`))")
        database.execSQL("CREATE INDEX `index_task_category_cross_ref_taskId` ON `task_category_cross_ref` (`taskId`)")
        database.execSQL("CREATE INDEX `index_task_category_cross_ref_categoryId` ON `task_category_cross_ref` (`categoryId`)")
        database.execSQL(
            "INSERT INTO task_category_cross_ref (taskId, categoryId) " +
                    "SELECT taskId, tagId as categoryId " +
                    "FROM TaskTagCrossRef;"
        )
        database.execSQL(
            "DROP TABLE TaskTagCrossRef;"
        )

        /* Replace "tags" table with "categories" */
        database.execSQL("CREATE TABLE `categories` (`categoryId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `color` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)")
        database.execSQL(
            "INSERT INTO categories (categoryId, title, color, createdAt, updatedAt) " +
                    "SELECT tagId as categoryId , title, color, createdAt, updatedAt " +
                    "FROM tags;"
        )
        database.execSQL(
            "DROP TABLE tags;"
        )

        /* Insert three new columns to "tasks" table */
        database.execSQL(
            "ALTER TABLE tasks ADD COLUMN doneDate INTEGER"
        )
        database.execSQL(
            "ALTER TABLE tasks ADD COLUMN dueTime INTEGER"
        )
        database.execSQL(
            "ALTER TABLE tasks ADD COLUMN priority INTEGER DEFAULT 0 NOT NULL"
        )
    }
}