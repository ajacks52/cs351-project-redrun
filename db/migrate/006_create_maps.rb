require 'active_record'

# Migration to create the Buttons tables
class CreateMaps < ActiveRecord::Migration

  def change
    create_table :maps do |t|
      t.column :map_name, :string, null: false, unique: true
    end
    add_index :maps, [:map_name], :name => "index_maps_on_map_name"
  end
end