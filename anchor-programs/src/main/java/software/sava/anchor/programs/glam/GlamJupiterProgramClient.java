package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;
import systems.comodal.jsoniter.JsonIterator;

import java.util.*;

import static software.sava.rpc.json.PublicKeyEncoding.parseBase58Encoded;
import static software.sava.solana.web2.jupiter.client.http.response.JupiterSwapInstructions.parseInstruction;

public interface GlamJupiterProgramClient {

  static GlamJupiterProgramClient createClient(final GlamProgramAccountClient nativeProgramAccountClient,
                                               final JupiterAccounts jupiterAccounts) {
    return new GlamJupiterProgramClientImpl(nativeProgramAccountClient, jupiterAccounts);
  }

  static GlamJupiterProgramClient createClient(final GlamProgramAccountClient nativeProgramAccountClient) {
    return createClient(nativeProgramAccountClient, JupiterAccounts.MAIN_NET);
  }

  /// Removes signature requirements for the vault/owner key.
  ///
  /// Jupiter assumes a direct call instead of a CPI call, which implicitly considers the calling program to be authorized.
  static Instruction fixCPICallerRights(final Instruction swapIx) {
    final var accounts = swapIx.accounts().toArray(AccountMeta[]::new);
    for (int i = 0; i < accounts.length; i++) {
      final var account = accounts[i];
      if (account.signer()) {
        accounts[i] = account.write()
            ? AccountMeta.createWrite(account.publicKey())
            : AccountMeta.createRead(account.publicKey());
        break;
      }
    }
    return Instruction.createInstruction(
        swapIx.programId(),
        Arrays.asList(accounts),
        swapIx.data()
    );
  }

  static Instruction parseSwapInstruction(final JsonIterator jsonResponseBody) {
    if (jsonResponseBody.skipUntil("swapInstruction") == null) {
      if (jsonResponseBody.reset(0).skipUntil("swapInstruction") == null) {
        return null;
      }
    }
    return parseInstruction(jsonResponseBody);
  }

  static Collection<PublicKey> parseLookupTables(final JsonIterator jsonResponseBody) {
    if (jsonResponseBody.skipUntil("addressLookupTableAddresses") == null) {
      if (jsonResponseBody.reset(0).skipUntil("addressLookupTableAddresses") == null) {
        return List.of();
      }
    }
    final var addressLookupTableAddresses = new ArrayList<PublicKey>();
    while (jsonResponseBody.readArray()) {
      addressLookupTableAddresses.add(parseBase58Encoded(jsonResponseBody));
    }
    return addressLookupTableAddresses;
  }

  SolanaAccounts solanaAccounts();

  GlamVaultAccounts glamVaultAccounts();

  JupiterAccounts jupiterAccounts();

  default List<Instruction> swapChecked(final PublicKey inputMintKey,
                                        final AccountMeta inputTokenProgram,
                                        final PublicKey outputMintKey,
                                        final AccountMeta outputTokenProgram,
                                        final long amount,
                                        final Instruction swapInstruction,
                                        final boolean wrapSOL) {
    return swapChecked(
        null, inputMintKey, inputTokenProgram,
        null, outputMintKey, outputTokenProgram,
        amount,
        swapInstruction,
        wrapSOL
    );
  }

  default List<Instruction> swapChecked(final PublicKey inputMintKey,
                                        final PublicKey outputMintKey,
                                        final long amount,
                                        final Instruction swapInstruction,
                                        final boolean wrapSOL) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapChecked(inputMintKey, tokenProgram, outputMintKey, tokenProgram, amount, swapInstruction, wrapSOL);
  }

  default List<Instruction> swapChecked(final PublicKey inputMintKey,
                                        final PublicKey outputMintKey,
                                        final long amount,
                                        final Instruction swapInstruction) {
    return swapChecked(inputMintKey, outputMintKey, amount, swapInstruction, true);
  }

  NativeProgramAccountClient nativeProgramAccountClient();

  Map<PublicKey, Instruction> createSwapTokenAccountsIdempotent(final AccountMeta inputTokenProgram,
                                                                final PublicKey inputMintKey,
                                                                final AccountMeta outputTokenProgram,
                                                                final PublicKey outputMintKey);

  default Instruction swapUncheckedAndNoWrap(final PublicKey inputMintKey,
                                             final AccountMeta inputTokenProgram,
                                             final PublicKey outputMintKey,
                                             final AccountMeta outputTokenProgram,
                                             final Instruction swapInstruction) {
    return swapUncheckedAndNoWrap(
        null, inputMintKey, inputTokenProgram,
        null, outputMintKey, outputTokenProgram,
        swapInstruction
    );
  }

  default Instruction swapUncheckedAndNoWrap(final PublicKey inputProgramStateKey,
                                             final PublicKey inputMintKey,
                                             final PublicKey outputProgramStateKey,
                                             final PublicKey outputMintKey,
                                             final Instruction swapInstruction) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapUncheckedAndNoWrap(
        inputProgramStateKey, inputMintKey, tokenProgram,
        outputProgramStateKey, outputMintKey, tokenProgram,
        swapInstruction
    );
  }

  default Instruction swapUncheckedAndNoWrap(final PublicKey inputMintKey,
                                             final PublicKey outputMintKey,
                                             final Instruction swapInstruction) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapUncheckedAndNoWrap(
        null, inputMintKey, tokenProgram,
        null, outputMintKey, tokenProgram,
        swapInstruction
    );
  }

  default List<Instruction> swapUnchecked(final PublicKey inputMintKey,
                                          final AccountMeta inputTokenProgram,
                                          final PublicKey outputMintKey,
                                          final AccountMeta outputTokenProgram,
                                          long amount,
                                          final Instruction swapInstruction,
                                          final boolean wrapSOL) {
    return swapUnchecked(
        null, inputMintKey, inputTokenProgram,
        null, outputMintKey, outputTokenProgram,
        amount,
        swapInstruction,
        wrapSOL
    );
  }

  default List<Instruction> swapUnchecked(final PublicKey inputMintKey,
                                          final PublicKey outputMintKey,
                                          final long amount,
                                          final Instruction swapInstruction,
                                          final boolean wrapSOL) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapUnchecked(inputMintKey, tokenProgram, outputMintKey, tokenProgram, amount, swapInstruction, wrapSOL);
  }

  default List<Instruction> swapUnchecked(final PublicKey inputMintKey,
                                          final PublicKey outputMintKey,
                                          final long amount,
                                          final Instruction swapInstruction) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapUnchecked(inputMintKey, tokenProgram, outputMintKey, tokenProgram, amount, swapInstruction, true);
  }

  // in/out program state keys may be used to check permission for swapping assets more generically.
  // For example, a user may have access to swap any LST;
  // this is checked by also passing the corresponding stake pool state program.

  default List<Instruction> swapChecked(final PublicKey inputProgramStateKey,
                                        final PublicKey inputMintKey,
                                        final PublicKey outputProgramStateKey,
                                        final PublicKey outputMintKey,
                                        final long amount,
                                        final Instruction swapInstruction,
                                        final boolean wrapSOL) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapChecked(
        inputProgramStateKey, inputMintKey, tokenProgram,
        outputProgramStateKey, outputMintKey, tokenProgram,
        amount, swapInstruction, wrapSOL
    );
  }

  default List<Instruction> swapChecked(final PublicKey inputProgramStateKey,
                                        final PublicKey inputMintKey,
                                        final PublicKey outputProgramStateKey,
                                        final PublicKey outputMintKey,
                                        final long amount,
                                        final Instruction swapInstruction) {
    return swapChecked(
        inputProgramStateKey, inputMintKey,
        outputProgramStateKey, outputMintKey,
        amount, swapInstruction, true
    );
  }

  List<Instruction> swapChecked(final PublicKey inputProgramStateKey,
                                final PublicKey inputMintKey,
                                final AccountMeta inputTokenProgram,
                                final PublicKey outputProgramStateKey,
                                final PublicKey outputMintKey,
                                final AccountMeta outputTokenProgram,
                                final long amount,
                                final Instruction swapInstruction,
                                final boolean wrapSOL);

  Instruction swapUncheckedAndNoWrap(final PublicKey inputProgramStateKey,
                                     final PublicKey inputMintKey,
                                     final AccountMeta inputTokenProgram,
                                     final PublicKey outputProgramStateKey,
                                     final PublicKey outputMintKey,
                                     final AccountMeta outputTokenProgram,
                                     final Instruction swapInstruction);

  default List<Instruction> swapUnchecked(final PublicKey inputProgramStateKey,
                                          final PublicKey inputMintKey,
                                          final PublicKey outputProgramStateKey,
                                          final PublicKey outputMintKey,
                                          final long amount,
                                          final Instruction swapInstruction,
                                          final boolean wrapSOL) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapUnchecked(
        inputProgramStateKey, inputMintKey, tokenProgram,
        outputProgramStateKey, outputMintKey, tokenProgram,
        amount, swapInstruction, wrapSOL
    );
  }

  default List<Instruction> swapUnchecked(final PublicKey inputProgramStateKey,
                                          final PublicKey inputMintKey,
                                          final PublicKey outputProgramStateKey,
                                          final PublicKey outputMintKey,
                                          final long amount,
                                          final Instruction swapInstruction) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapUnchecked(
        inputProgramStateKey, inputMintKey, tokenProgram,
        outputProgramStateKey, outputMintKey, tokenProgram,
        amount, swapInstruction, true
    );
  }

  List<Instruction> swapUnchecked(final PublicKey inputProgramStateKey,
                                  final PublicKey inputMintKey,
                                  final AccountMeta inputTokenProgram,
                                  final PublicKey outputProgramStateKey,
                                  final PublicKey outputMintKey,
                                  final AccountMeta outputTokenProgram,
                                  final long amount,
                                  final Instruction swapInstruction,
                                  final boolean wrapSOL);
}
