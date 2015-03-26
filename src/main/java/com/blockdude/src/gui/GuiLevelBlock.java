package com.blockdude.src.gui;

import org.newdawn.slick.Color;

import com.blockdude.src.BlockDude;
import com.blockdude.src.fonts.Fonts;
import com.blockdude.src.screens.Screens;
import com.blockdude.src.shapes.ShapesHelper;

public class GuiLevelBlock extends GuiElement {
	private float x;
	private float y;
	
	// The width and height are going to be fixed
	
	private int worldId;
	private int levelId;
	
	private GuiIconButton viewScoreboardButton;
	private GuiIconButton playButton;
	
	public GuiLevelBlock(int world, int level, float x, float y) {
		this.worldId = world;
		this.levelId = level;
		
		this.x = x;
		this.y = y;
		
		this.viewScoreboardButton = new GuiIconButton(x + 90, y + 125, 20, 21, new GuiIconButton.IconButtonInterface() {
			@Override
			public void render(GuiIconButton button) {
				ShapesHelper.rect(button.getX(), button.getY(), button.getHitbox().getWidth(), 4, Color.white);
				ShapesHelper.rect(button.getX(), button.getY() + 8, button.getHitbox().getWidth(), 4, Color.white);
				ShapesHelper.rect(button.getX(), button.getY() + 16, button.getHitbox().getWidth(), 4, Color.white);
			}
			
			@Override
			public void onClick() {
				
			}
		});
		this.playButton = new GuiIconButton(x + 50, y + 70, 20, 20, new GuiIconButton.IconButtonInterface() {
			@Override
			public void render(GuiIconButton button) {
				if (button.isActive()) {
					ShapesHelper.circle(button.getX() + 10, button.getY() + 10, 20, new Color(0, 0, 0, 0.45F));
				}
				ShapesHelper.triangle(button.getX() + 4, button.getY(), button.getX() + 4, button.getY() + 20, button.getX() + 20, button.getY() + 10, new Color(1.0F, 1.0F, 1.0F, 0.75F));
			}
			
			@Override
			public void onClick() {
				BlockDude.setScreen(Screens.GAME);
			}
		});
	}

	@Override
	public void render() {
		Color col = new Color(46, 204, 113);
		if (this.worldId == 1) {
			col = new Color(155, 89, 182);
		} else if (this.worldId == 2) {
			col = new Color(231, 76, 60);
		}
		ShapesHelper.rect(this.x, this.y, 120, 160, col);
		ShapesHelper.rect(this.x, this.y + 110, 120, 50, new Color(0, 0, 0, 0.15F));
		
		Fonts.MONTSERRAT.drawCenteredString(48, this.x + 60, this.y + 35, this.worldId + "-" + this.levelId, Color.white);
		
		this.viewScoreboardButton.render();
		this.playButton.render();
	}

	@Override
	public void update() {
		this.viewScoreboardButton.update();
		this.playButton.update();
	}
}
