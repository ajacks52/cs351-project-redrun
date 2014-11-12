require 'active_record'

# Migration to create the Characters tables
class CreateCharacters < ActiveRecord::Migration

  def change
    create_table :characters do |t|
      t.column :character_name, :string, null: false, unique: true
      t.column :image, :string, null: false
      t.column :team, :string, null: false
    end
    add_index :characters, [:character_name], :name => "index_characters_on_character_name"
    add_index :characters, [:image], :name => "index_characters_on_image"
    add_index :characters, [:team], :name => "index_characters_on_team"
  end
end