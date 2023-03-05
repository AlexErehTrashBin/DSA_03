package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.cli;

import org.apache.commons.cli.*;

public class CLIUtils {
	public static final String WINDOWED_HT = "Запустить с оконным интерфейсом.";
	public static final String HELP_HT = "Показать справку.";
	public static final String OUTPUT_HT = "Выходной файл.";

	public static Options fillOptions(){
		Options options = new Options();
		Option windowed = Option.builder("w")
				.longOpt("windowed")
				.desc(WINDOWED_HT)
				.build();
		Option help = Option.builder("h")
				.longOpt("help")
				.desc(HELP_HT)
				.build();
		Option output = Option.builder("o")
				.longOpt("output-file")
				.hasArg()
				.desc(OUTPUT_HT)
				.build();
		options.addOption(windowed);
		options.addOption(help);
		options.addOption(output);

		return options;
	}

	public static InputArgs parseCmdArgs(String[] args) {
		InputArgs inputArgs = new InputArgs();
		if (args == null) return inputArgs;
		Options options = fillOptions();
		CommandLine line;
		try {
			line = new DefaultParser().parse(options, args);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return inputArgs;
		}

		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("<cmd>", options);
			return inputArgs;
		}
		if (line.hasOption("w")) {
			System.out.println("Будет запущен оконный интерфейс");
			inputArgs.window = true;
		}
		if (line.hasOption("o")) {
			String outputFile = line.getOptionValue("o");
			System.out.printf("Будет обработан следующий выходной файл: %s%n", outputFile);
			inputArgs.outputFile = outputFile;
			inputArgs.runIndividualFileCheck = true;
		}
		return inputArgs;
	}
}
