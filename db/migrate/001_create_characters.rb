require 'active_record'

# Migration to create the Characters tables
class CreateCharacters < ActiveRecord::Migration

  def change
    create_table :characters do |t|
      t.column :character_name, :string, null: false, unique: true
      t.column :image, :string, null: false
      t.column :team, :string, null: false
      t.column :start_loc, :string, null: false, unique: true
      t.belongs_to :map, null: false
    end
    add_index :characters, [:character_name], :name => "index_characters_on_character_name"
    add_index :characters, [:image], :name => "index_characters_on_image"
    add_index :characters, [:team], :name => "index_characters_on_team"
    add_index :characters, [:start_loc], :name => "index_characters_on_start_loc"
    add_index :characters, [:map_id], :name => "index_characaters_on_map_id"
  end
end