package com.ryderbelserion.cluster.api.builders;

import com.ryderbelserion.cluster.utils.AdvUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.NotNull;

public class ComponentBuilder {

    private final TextComponent.@NotNull Builder builder = Component.text();

    private final Audience target;

    public ComponentBuilder(Audience target) {
        this.target = target;
    }

    /**
     * Appends a new component to the message.
     *
     * @param component the component to append.
     * @return the Builder object with updated information.
     */
    public ComponentBuilder append(Component component) {
        this.builder.append(component);

        return this;
    }

    /**
     * Appends a new component to the message.
     *
     * @param text the text shown on hover.
     * @return the Builder object with updated information.
     */
    public ComponentBuilder hover(String text) {
        this.builder.hoverEvent(HoverEvent.showText(AdvUtils.parse(text)));

        return this;
    }

    /**
     * Adds a click event to the message.
     *
     * @param action the click action to use.
     * @param text the text that is sent on click.
     * @return the Builder object with updated information.
     */
    public ComponentBuilder click(ClickEvent.Action action, String text) {
        this.builder.clickEvent(ClickEvent.clickEvent(action, text));

        return this;
    }

    /**
     * @return the final component.
     */
    @NotNull
    public TextComponent getComponent() {
        Component message = AdvUtils.parse(this.value);

        return this.builder.append(message).build();
    }

    /**
     * Send a component to someone.
     */
    public void send() {
        this.target.sendMessage(getComponent());
    }

    /**
     * @return get the target recipient.
     */
    public Audience getTarget() {
        return this.target;
    }

    private String value;

    /**
     * Sets the message to the value.
     *
     * @param value the string to use.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the message.
     */
    public String getValue() {
        return this.value;
    }
}