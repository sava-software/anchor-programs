package software.sava.anchor.programs.jupiter.swap.anchor;

import java.util.List;

import software.sava.anchor.programs.jupiter.swap.anchor.types.RoutePlanStep;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class JupiterProgram {

  public static final Discriminator ROUTE_DISCRIMINATOR = toDiscriminator(229, 23, 203, 151, 122, 227, 173, 42);

  public static Instruction route(final AccountMeta invokedJupiterProgramMeta,
                                  final PublicKey tokenProgramKey,
                                  final PublicKey userTransferAuthorityKey,
                                  final PublicKey userSourceTokenAccountKey,
                                  final PublicKey userDestinationTokenAccountKey,
                                  final PublicKey destinationTokenAccountKey,
                                  final PublicKey destinationMintKey,
                                  final PublicKey platformFeeAccountKey,
                                  final PublicKey eventAuthorityKey,
                                  final PublicKey programKey,
                                  final RoutePlanStep[] routePlan,
                                  final long inAmount,
                                  final long quotedOutAmount,
                                  final int slippageBps,
                                  final int platformFeeBps) {
    final var keys = List.of(
      createRead(tokenProgramKey),
      createReadOnlySigner(userTransferAuthorityKey),
      createWrite(userSourceTokenAccountKey),
      createWrite(userDestinationTokenAccountKey),
      createWrite(destinationTokenAccountKey),
      createRead(destinationMintKey),
      createWrite(platformFeeAccountKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[27 + Borsh.len(routePlan)];
    int i = writeDiscriminator(ROUTE_DISCRIMINATOR, _data, 0);
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, inAmount);
    i += 8;
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }


public record RouteData(RoutePlanStep[] routePlan,
                        long inAmount,
                        long quotedOutAmount,
                        int slippageBps,
                        int platformFeeBps) implements Borsh {

  public static RouteData read(final byte[] _data, final int offset) {
    int i = offset;
    final var routePlan = Borsh.readVector(RoutePlanStep.class, RoutePlanStep::read, _data, i);
    i += Borsh.len(routePlan);
    final var inAmount = getInt64LE(_data, i);
    i += 8;
    final var quotedOutAmount = getInt64LE(_data, i);
    i += 8;
    final var slippageBps = getInt16LE(_data, i);
    i += 2;
    final var platformFeeBps = _data[i] & 0xFF;
    return new RouteData(routePlan,
                         inAmount,
                         quotedOutAmount,
                         slippageBps,
                         platformFeeBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, inAmount);
    i += 8;
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(routePlan)
         + 8
         + 8
         + 2
         + 1;
  }
}

  public static final Discriminator ROUTE_WITH_TOKEN_LEDGER_DISCRIMINATOR = toDiscriminator(150, 86, 71, 116, 167, 93, 14, 104);

  public static Instruction routeWithTokenLedger(final AccountMeta invokedJupiterProgramMeta,
                                                 final PublicKey tokenProgramKey,
                                                 final PublicKey userTransferAuthorityKey,
                                                 final PublicKey userSourceTokenAccountKey,
                                                 final PublicKey userDestinationTokenAccountKey,
                                                 final PublicKey destinationTokenAccountKey,
                                                 final PublicKey destinationMintKey,
                                                 final PublicKey platformFeeAccountKey,
                                                 final PublicKey tokenLedgerKey,
                                                 final PublicKey eventAuthorityKey,
                                                 final PublicKey programKey,
                                                 final RoutePlanStep[] routePlan,
                                                 final long quotedOutAmount,
                                                 final int slippageBps,
                                                 final int platformFeeBps) {
    final var keys = List.of(
      createRead(tokenProgramKey),
      createReadOnlySigner(userTransferAuthorityKey),
      createWrite(userSourceTokenAccountKey),
      createWrite(userDestinationTokenAccountKey),
      createWrite(destinationTokenAccountKey),
      createRead(destinationMintKey),
      createWrite(platformFeeAccountKey),
      createRead(tokenLedgerKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[19 + Borsh.len(routePlan)];
    int i = writeDiscriminator(ROUTE_WITH_TOKEN_LEDGER_DISCRIMINATOR, _data, 0);
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }


public record RouteWithTokenLedgerData(RoutePlanStep[] routePlan,
                                       long quotedOutAmount,
                                       int slippageBps,
                                       int platformFeeBps) implements Borsh {

  public static RouteWithTokenLedgerData read(final byte[] _data, final int offset) {
    int i = offset;
    final var routePlan = Borsh.readVector(RoutePlanStep.class, RoutePlanStep::read, _data, i);
    i += Borsh.len(routePlan);
    final var quotedOutAmount = getInt64LE(_data, i);
    i += 8;
    final var slippageBps = getInt16LE(_data, i);
    i += 2;
    final var platformFeeBps = _data[i] & 0xFF;
    return new RouteWithTokenLedgerData(routePlan,
                                        quotedOutAmount,
                                        slippageBps,
                                        platformFeeBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(routePlan) + 8 + 2 + 1;
  }
}

  public static final Discriminator EXACT_OUT_ROUTE_DISCRIMINATOR = toDiscriminator(208, 51, 239, 151, 123, 43, 237, 92);

  public static Instruction exactOutRoute(final AccountMeta invokedJupiterProgramMeta,
                                          final PublicKey tokenProgramKey,
                                          final PublicKey userTransferAuthorityKey,
                                          final PublicKey userSourceTokenAccountKey,
                                          final PublicKey userDestinationTokenAccountKey,
                                          final PublicKey destinationTokenAccountKey,
                                          final PublicKey sourceMintKey,
                                          final PublicKey destinationMintKey,
                                          final PublicKey platformFeeAccountKey,
                                          final PublicKey token2022ProgramKey,
                                          final PublicKey eventAuthorityKey,
                                          final PublicKey programKey,
                                          final RoutePlanStep[] routePlan,
                                          final long outAmount,
                                          final long quotedInAmount,
                                          final int slippageBps,
                                          final int platformFeeBps) {
    final var keys = List.of(
      createRead(tokenProgramKey),
      createReadOnlySigner(userTransferAuthorityKey),
      createWrite(userSourceTokenAccountKey),
      createWrite(userDestinationTokenAccountKey),
      createWrite(destinationTokenAccountKey),
      createRead(sourceMintKey),
      createRead(destinationMintKey),
      createWrite(platformFeeAccountKey),
      createRead(token2022ProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[27 + Borsh.len(routePlan)];
    int i = writeDiscriminator(EXACT_OUT_ROUTE_DISCRIMINATOR, _data, 0);
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, outAmount);
    i += 8;
    putInt64LE(_data, i, quotedInAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }


public record ExactOutRouteData(RoutePlanStep[] routePlan,
                                long outAmount,
                                long quotedInAmount,
                                int slippageBps,
                                int platformFeeBps) implements Borsh {

  public static ExactOutRouteData read(final byte[] _data, final int offset) {
    int i = offset;
    final var routePlan = Borsh.readVector(RoutePlanStep.class, RoutePlanStep::read, _data, i);
    i += Borsh.len(routePlan);
    final var outAmount = getInt64LE(_data, i);
    i += 8;
    final var quotedInAmount = getInt64LE(_data, i);
    i += 8;
    final var slippageBps = getInt16LE(_data, i);
    i += 2;
    final var platformFeeBps = _data[i] & 0xFF;
    return new ExactOutRouteData(routePlan,
                                 outAmount,
                                 quotedInAmount,
                                 slippageBps,
                                 platformFeeBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, outAmount);
    i += 8;
    putInt64LE(_data, i, quotedInAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(routePlan)
         + 8
         + 8
         + 2
         + 1;
  }
}

  public static final Discriminator SHARED_ACCOUNTS_ROUTE_DISCRIMINATOR = toDiscriminator(193, 32, 155, 51, 65, 214, 156, 129);

  public static Instruction sharedAccountsRoute(final AccountMeta invokedJupiterProgramMeta,
                                                final PublicKey tokenProgramKey,
                                                final PublicKey programAuthorityKey,
                                                final PublicKey userTransferAuthorityKey,
                                                final PublicKey sourceTokenAccountKey,
                                                final PublicKey programSourceTokenAccountKey,
                                                final PublicKey programDestinationTokenAccountKey,
                                                final PublicKey destinationTokenAccountKey,
                                                final PublicKey sourceMintKey,
                                                final PublicKey destinationMintKey,
                                                final PublicKey platformFeeAccountKey,
                                                final PublicKey token2022ProgramKey,
                                                final PublicKey eventAuthorityKey,
                                                final PublicKey programKey,
                                                final int id,
                                                final RoutePlanStep[] routePlan,
                                                final long inAmount,
                                                final long quotedOutAmount,
                                                final int slippageBps,
                                                final int platformFeeBps) {
    final var keys = List.of(
      createRead(tokenProgramKey),
      createRead(programAuthorityKey),
      createReadOnlySigner(userTransferAuthorityKey),
      createWrite(sourceTokenAccountKey),
      createWrite(programSourceTokenAccountKey),
      createWrite(programDestinationTokenAccountKey),
      createWrite(destinationTokenAccountKey),
      createRead(sourceMintKey),
      createRead(destinationMintKey),
      createWrite(platformFeeAccountKey),
      createRead(token2022ProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[28 + Borsh.len(routePlan)];
    int i = writeDiscriminator(SHARED_ACCOUNTS_ROUTE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;
    ++i;
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, inAmount);
    i += 8;
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }


public record SharedAccountsRouteData(int id,
                                      RoutePlanStep[] routePlan,
                                      long inAmount,
                                      long quotedOutAmount,
                                      int slippageBps,
                                      int platformFeeBps) implements Borsh {

  public static SharedAccountsRouteData read(final byte[] _data, final int offset) {
    int i = offset;
    final var id = _data[i] & 0xFF;
    ++i;
    final var routePlan = Borsh.readVector(RoutePlanStep.class, RoutePlanStep::read, _data, i);
    i += Borsh.len(routePlan);
    final var inAmount = getInt64LE(_data, i);
    i += 8;
    final var quotedOutAmount = getInt64LE(_data, i);
    i += 8;
    final var slippageBps = getInt16LE(_data, i);
    i += 2;
    final var platformFeeBps = _data[i] & 0xFF;
    return new SharedAccountsRouteData(id,
                                       routePlan,
                                       inAmount,
                                       quotedOutAmount,
                                       slippageBps,
                                       platformFeeBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) id;
    ++i;
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, inAmount);
    i += 8;
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return 1
         + Borsh.len(routePlan)
         + 8
         + 8
         + 2
         + 1;
  }
}

  public static final Discriminator SHARED_ACCOUNTS_ROUTE_WITH_TOKEN_LEDGER_DISCRIMINATOR = toDiscriminator(230, 121, 143, 80, 119, 159, 106, 170);

  public static Instruction sharedAccountsRouteWithTokenLedger(final AccountMeta invokedJupiterProgramMeta,
                                                               final PublicKey tokenProgramKey,
                                                               final PublicKey programAuthorityKey,
                                                               final PublicKey userTransferAuthorityKey,
                                                               final PublicKey sourceTokenAccountKey,
                                                               final PublicKey programSourceTokenAccountKey,
                                                               final PublicKey programDestinationTokenAccountKey,
                                                               final PublicKey destinationTokenAccountKey,
                                                               final PublicKey sourceMintKey,
                                                               final PublicKey destinationMintKey,
                                                               final PublicKey platformFeeAccountKey,
                                                               final PublicKey token2022ProgramKey,
                                                               final PublicKey tokenLedgerKey,
                                                               final PublicKey eventAuthorityKey,
                                                               final PublicKey programKey,
                                                               final int id,
                                                               final RoutePlanStep[] routePlan,
                                                               final long quotedOutAmount,
                                                               final int slippageBps,
                                                               final int platformFeeBps) {
    final var keys = List.of(
      createRead(tokenProgramKey),
      createRead(programAuthorityKey),
      createReadOnlySigner(userTransferAuthorityKey),
      createWrite(sourceTokenAccountKey),
      createWrite(programSourceTokenAccountKey),
      createWrite(programDestinationTokenAccountKey),
      createWrite(destinationTokenAccountKey),
      createRead(sourceMintKey),
      createRead(destinationMintKey),
      createWrite(platformFeeAccountKey),
      createRead(token2022ProgramKey),
      createRead(tokenLedgerKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[20 + Borsh.len(routePlan)];
    int i = writeDiscriminator(SHARED_ACCOUNTS_ROUTE_WITH_TOKEN_LEDGER_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;
    ++i;
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }


public record SharedAccountsRouteWithTokenLedgerData(int id,
                                                     RoutePlanStep[] routePlan,
                                                     long quotedOutAmount,
                                                     int slippageBps,
                                                     int platformFeeBps) implements Borsh {

  public static SharedAccountsRouteWithTokenLedgerData read(final byte[] _data, final int offset) {
    int i = offset;
    final var id = _data[i] & 0xFF;
    ++i;
    final var routePlan = Borsh.readVector(RoutePlanStep.class, RoutePlanStep::read, _data, i);
    i += Borsh.len(routePlan);
    final var quotedOutAmount = getInt64LE(_data, i);
    i += 8;
    final var slippageBps = getInt16LE(_data, i);
    i += 2;
    final var platformFeeBps = _data[i] & 0xFF;
    return new SharedAccountsRouteWithTokenLedgerData(id,
                                                      routePlan,
                                                      quotedOutAmount,
                                                      slippageBps,
                                                      platformFeeBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) id;
    ++i;
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return 1
         + Borsh.len(routePlan)
         + 8
         + 2
         + 1;
  }
}

  public static final Discriminator SHARED_ACCOUNTS_EXACT_OUT_ROUTE_DISCRIMINATOR = toDiscriminator(176, 209, 105, 168, 154, 125, 69, 62);

  public static Instruction sharedAccountsExactOutRoute(final AccountMeta invokedJupiterProgramMeta,
                                                        final PublicKey tokenProgramKey,
                                                        final PublicKey programAuthorityKey,
                                                        final PublicKey userTransferAuthorityKey,
                                                        final PublicKey sourceTokenAccountKey,
                                                        final PublicKey programSourceTokenAccountKey,
                                                        final PublicKey programDestinationTokenAccountKey,
                                                        final PublicKey destinationTokenAccountKey,
                                                        final PublicKey sourceMintKey,
                                                        final PublicKey destinationMintKey,
                                                        final PublicKey platformFeeAccountKey,
                                                        final PublicKey token2022ProgramKey,
                                                        final PublicKey eventAuthorityKey,
                                                        final PublicKey programKey,
                                                        final int id,
                                                        final RoutePlanStep[] routePlan,
                                                        final long outAmount,
                                                        final long quotedInAmount,
                                                        final int slippageBps,
                                                        final int platformFeeBps) {
    final var keys = List.of(
      createRead(tokenProgramKey),
      createRead(programAuthorityKey),
      createReadOnlySigner(userTransferAuthorityKey),
      createWrite(sourceTokenAccountKey),
      createWrite(programSourceTokenAccountKey),
      createWrite(programDestinationTokenAccountKey),
      createWrite(destinationTokenAccountKey),
      createRead(sourceMintKey),
      createRead(destinationMintKey),
      createWrite(platformFeeAccountKey),
      createRead(token2022ProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[28 + Borsh.len(routePlan)];
    int i = writeDiscriminator(SHARED_ACCOUNTS_EXACT_OUT_ROUTE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;
    ++i;
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, outAmount);
    i += 8;
    putInt64LE(_data, i, quotedInAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }


public record SharedAccountsExactOutRouteData(int id,
                                              RoutePlanStep[] routePlan,
                                              long outAmount,
                                              long quotedInAmount,
                                              int slippageBps,
                                              int platformFeeBps) implements Borsh {

  public static SharedAccountsExactOutRouteData read(final byte[] _data, final int offset) {
    int i = offset;
    final var id = _data[i] & 0xFF;
    ++i;
    final var routePlan = Borsh.readVector(RoutePlanStep.class, RoutePlanStep::read, _data, i);
    i += Borsh.len(routePlan);
    final var outAmount = getInt64LE(_data, i);
    i += 8;
    final var quotedInAmount = getInt64LE(_data, i);
    i += 8;
    final var slippageBps = getInt16LE(_data, i);
    i += 2;
    final var platformFeeBps = _data[i] & 0xFF;
    return new SharedAccountsExactOutRouteData(id,
                                               routePlan,
                                               outAmount,
                                               quotedInAmount,
                                               slippageBps,
                                               platformFeeBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) id;
    ++i;
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, outAmount);
    i += 8;
    putInt64LE(_data, i, quotedInAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return 1
         + Borsh.len(routePlan)
         + 8
         + 8
         + 2
         + 1;
  }
}

  public static final Discriminator SET_TOKEN_LEDGER_DISCRIMINATOR = toDiscriminator(228, 85, 185, 112, 78, 79, 77, 2);

  public static Instruction setTokenLedger(final AccountMeta invokedJupiterProgramMeta, final PublicKey tokenLedgerKey, final PublicKey tokenAccountKey) {
    final var keys = List.of(
      createWrite(tokenLedgerKey),
      createRead(tokenAccountKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, SET_TOKEN_LEDGER_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_OPEN_ORDERS_DISCRIMINATOR = toDiscriminator(229, 194, 212, 172, 8, 10, 134, 147);

  public static Instruction createOpenOrders(final AccountMeta invokedJupiterProgramMeta,
                                             final PublicKey openOrdersKey,
                                             final PublicKey payerKey,
                                             final PublicKey dexProgramKey,
                                             final PublicKey systemProgramKey,
                                             final PublicKey rentKey,
                                             final PublicKey marketKey) {
    final var keys = List.of(
      createWrite(openOrdersKey),
      createWritableSigner(payerKey),
      createRead(dexProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(marketKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, CREATE_OPEN_ORDERS_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_TOKEN_ACCOUNT_DISCRIMINATOR = toDiscriminator(147, 241, 123, 100, 244, 132, 174, 118);

  public static Instruction createTokenAccount(final AccountMeta invokedJupiterProgramMeta,
                                               final PublicKey tokenAccountKey,
                                               final PublicKey userKey,
                                               final PublicKey mintKey,
                                               final PublicKey tokenProgramKey,
                                               final PublicKey systemProgramKey,
                                               final int bump) {
    final var keys = List.of(
      createWrite(tokenAccountKey),
      createWritableSigner(userKey),
      createRead(mintKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CREATE_TOKEN_ACCOUNT_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) bump;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }


public record CreateTokenAccountData(int bump) implements Borsh {

  public static final int BYTES = 1;

  public static CreateTokenAccountData read(final byte[] _data, final int offset) {
    final var bump = _data[offset] & 0xFF;
    return new CreateTokenAccountData(bump);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) bump;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator CREATE_PROGRAM_OPEN_ORDERS_DISCRIMINATOR = toDiscriminator(28, 226, 32, 148, 188, 136, 113, 171);

  public static Instruction createProgramOpenOrders(final AccountMeta invokedJupiterProgramMeta,
                                                    final PublicKey openOrdersKey,
                                                    final PublicKey payerKey,
                                                    final PublicKey programAuthorityKey,
                                                    final PublicKey dexProgramKey,
                                                    final PublicKey systemProgramKey,
                                                    final PublicKey rentKey,
                                                    final PublicKey marketKey,
                                                    final int id) {
    final var keys = List.of(
      createWrite(openOrdersKey),
      createWritableSigner(payerKey),
      createRead(programAuthorityKey),
      createRead(dexProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(marketKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CREATE_PROGRAM_OPEN_ORDERS_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }


public record CreateProgramOpenOrdersData(int id) implements Borsh {

  public static final int BYTES = 1;

  public static CreateProgramOpenOrdersData read(final byte[] _data, final int offset) {
    final var id = _data[offset] & 0xFF;
    return new CreateProgramOpenOrdersData(id);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) id;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator CLAIM_DISCRIMINATOR = toDiscriminator(62, 198, 214, 193, 213, 159, 108, 210);

  public static Instruction claim(final AccountMeta invokedJupiterProgramMeta,
                                  final PublicKey walletKey,
                                  final PublicKey programAuthorityKey,
                                  final PublicKey systemProgramKey,
                                  final int id) {
    final var keys = List.of(
      createWrite(walletKey),
      createWrite(programAuthorityKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CLAIM_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }


public record ClaimData(int id) implements Borsh {

  public static final int BYTES = 1;

  public static ClaimData read(final byte[] _data, final int offset) {
    final var id = _data[offset] & 0xFF;
    return new ClaimData(id);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) id;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator CLAIM_TOKEN_DISCRIMINATOR = toDiscriminator(116, 206, 27, 191, 166, 19, 0, 73);

  public static Instruction claimToken(final AccountMeta invokedJupiterProgramMeta,
                                       final PublicKey payerKey,
                                       final PublicKey walletKey,
                                       final PublicKey programAuthorityKey,
                                       final PublicKey programTokenAccountKey,
                                       final PublicKey destinationTokenAccountKey,
                                       final PublicKey mintKey,
                                       final PublicKey associatedTokenTokenProgramKey,
                                       final PublicKey associatedTokenProgramKey,
                                       final PublicKey systemProgramKey,
                                       final int id) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createRead(walletKey),
      createRead(programAuthorityKey),
      createWrite(programTokenAccountKey),
      createWrite(destinationTokenAccountKey),
      createRead(mintKey),
      createRead(associatedTokenTokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CLAIM_TOKEN_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }


public record ClaimTokenData(int id) implements Borsh {

  public static final int BYTES = 1;

  public static ClaimTokenData read(final byte[] _data, final int offset) {
    final var id = _data[offset] & 0xFF;
    return new ClaimTokenData(id);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) id;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator CREATE_TOKEN_LEDGER_DISCRIMINATOR = toDiscriminator(232, 242, 197, 253, 240, 143, 129, 52);

  public static Instruction createTokenLedger(final AccountMeta invokedJupiterProgramMeta,
                                              final PublicKey tokenLedgerKey,
                                              final PublicKey payerKey,
                                              final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWritableSigner(tokenLedgerKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, CREATE_TOKEN_LEDGER_DISCRIMINATOR);
  }

  public static final Discriminator MERCURIAL_SWAP_DISCRIMINATOR = toDiscriminator(2, 5, 77, 173, 197, 0, 7, 157);

  public static Instruction mercurialSwap(final AccountMeta invokedJupiterProgramMeta,
                                          final PublicKey swapProgramKey,
                                          final PublicKey swapStateKey,
                                          final PublicKey tokenProgramKey,
                                          final PublicKey poolAuthorityKey,
                                          final PublicKey userTransferAuthorityKey,
                                          final PublicKey sourceTokenAccountKey,
                                          final PublicKey destinationTokenAccountKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(swapStateKey),
      createRead(tokenProgramKey),
      createRead(poolAuthorityKey),
      createRead(userTransferAuthorityKey),
      createWrite(sourceTokenAccountKey),
      createWrite(destinationTokenAccountKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, MERCURIAL_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator CYKURA_SWAP_DISCRIMINATOR = toDiscriminator(38, 241, 21, 107, 120, 59, 184, 249);

  public static Instruction cykuraSwap(final AccountMeta invokedJupiterProgramMeta,
                                       final PublicKey swapProgramKey,
                                       final PublicKey signerKey,
                                       final PublicKey factoryStateKey,
                                       final PublicKey poolStateKey,
                                       final PublicKey inputTokenAccountKey,
                                       final PublicKey outputTokenAccountKey,
                                       final PublicKey inputVaultKey,
                                       final PublicKey outputVaultKey,
                                       final PublicKey lastObservationStateKey,
                                       final PublicKey coreProgramKey,
                                       final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(signerKey),
      createRead(factoryStateKey),
      createWrite(poolStateKey),
      createWrite(inputTokenAccountKey),
      createWrite(outputTokenAccountKey),
      createWrite(inputVaultKey),
      createWrite(outputVaultKey),
      createWrite(lastObservationStateKey),
      createRead(coreProgramKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, CYKURA_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator SERUM_SWAP_DISCRIMINATOR = toDiscriminator(88, 183, 70, 249, 214, 118, 82, 210);

  public static Instruction serumSwap(final AccountMeta invokedJupiterProgramMeta,
                                      final PublicKey marketKey,
                                      final PublicKey authorityKey,
                                      final PublicKey orderPayerTokenAccountKey,
                                      final PublicKey coinWalletKey,
                                      final PublicKey pcWalletKey,
                                      final PublicKey dexProgramKey,
                                      final PublicKey tokenProgramKey,
                                      final PublicKey rentKey) {
    final var keys = List.of(
      createRead(marketKey),
      createRead(authorityKey),
      createWrite(orderPayerTokenAccountKey),
      createWrite(coinWalletKey),
      createWrite(pcWalletKey),
      createRead(dexProgramKey),
      createRead(tokenProgramKey),
      createRead(rentKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, SERUM_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator SABER_SWAP_DISCRIMINATOR = toDiscriminator(64, 62, 98, 226, 52, 74, 37, 178);

  public static Instruction saberSwap(final AccountMeta invokedJupiterProgramMeta,
                                      final PublicKey swapProgramKey,
                                      final PublicKey tokenProgramKey,
                                      final PublicKey swapKey,
                                      final PublicKey swapAuthorityKey,
                                      final PublicKey userAuthorityKey,
                                      final PublicKey inputUserAccountKey,
                                      final PublicKey inputTokenAccountKey,
                                      final PublicKey outputUserAccountKey,
                                      final PublicKey outputTokenAccountKey,
                                      final PublicKey feesTokenAccountKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(tokenProgramKey),
      createRead(swapKey),
      createRead(swapAuthorityKey),
      createRead(userAuthorityKey),
      createWrite(inputUserAccountKey),
      createWrite(inputTokenAccountKey),
      createWrite(outputUserAccountKey),
      createWrite(outputTokenAccountKey),
      createWrite(feesTokenAccountKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, SABER_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator SABER_ADD_DECIMALS_DISCRIMINATOR = toDiscriminator(36, 53, 231, 184, 7, 181, 5, 238);

  public static Instruction saberAddDecimals(final AccountMeta invokedJupiterProgramMeta,
                                             final PublicKey addDecimalsProgramKey,
                                             final PublicKey wrapperKey,
                                             final PublicKey wrapperMintKey,
                                             final PublicKey wrapperUnderlyingTokensKey,
                                             final PublicKey ownerKey,
                                             final PublicKey userUnderlyingTokensKey,
                                             final PublicKey userWrappedTokensKey,
                                             final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(addDecimalsProgramKey),
      createRead(wrapperKey),
      createWrite(wrapperMintKey),
      createWrite(wrapperUnderlyingTokensKey),
      createRead(ownerKey),
      createWrite(userUnderlyingTokensKey),
      createWrite(userWrappedTokensKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, SABER_ADD_DECIMALS_DISCRIMINATOR);
  }

  public static final Discriminator TOKEN_SWAP_DISCRIMINATOR = toDiscriminator(187, 192, 118, 212, 62, 109, 28, 213);

  public static Instruction tokenSwap(final AccountMeta invokedJupiterProgramMeta,
                                      final PublicKey tokenSwapProgramKey,
                                      final PublicKey tokenProgramKey,
                                      final PublicKey swapKey,
                                      final PublicKey authorityKey,
                                      final PublicKey userTransferAuthorityKey,
                                      final PublicKey sourceKey,
                                      final PublicKey swapSourceKey,
                                      final PublicKey swapDestinationKey,
                                      final PublicKey destinationKey,
                                      final PublicKey poolMintKey,
                                      final PublicKey poolFeeKey) {
    final var keys = List.of(
      createRead(tokenSwapProgramKey),
      createRead(tokenProgramKey),
      createRead(swapKey),
      createRead(authorityKey),
      createRead(userTransferAuthorityKey),
      createWrite(sourceKey),
      createWrite(swapSourceKey),
      createWrite(swapDestinationKey),
      createWrite(destinationKey),
      createWrite(poolMintKey),
      createWrite(poolFeeKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, TOKEN_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator TOKEN_SWAP_V2_DISCRIMINATOR = toDiscriminator(51, 48, 145, 115, 123, 95, 71, 138);

  public static Instruction tokenSwapV2(final AccountMeta invokedJupiterProgramMeta,
                                        final PublicKey swapProgramKey,
                                        final PublicKey swapKey,
                                        final PublicKey authorityKey,
                                        final PublicKey userTransferAuthorityKey,
                                        final PublicKey sourceKey,
                                        final PublicKey swapSourceKey,
                                        final PublicKey swapDestinationKey,
                                        final PublicKey destinationKey,
                                        final PublicKey poolMintKey,
                                        final PublicKey poolFeeKey,
                                        final PublicKey sourceMintKey,
                                        final PublicKey destinationMintKey,
                                        final PublicKey sourceTokenProgramKey,
                                        final PublicKey destinationTokenProgramKey,
                                        final PublicKey poolTokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(swapKey),
      createRead(authorityKey),
      createRead(userTransferAuthorityKey),
      createWrite(sourceKey),
      createWrite(swapSourceKey),
      createWrite(swapDestinationKey),
      createWrite(destinationKey),
      createWrite(poolMintKey),
      createWrite(poolFeeKey),
      createRead(sourceMintKey),
      createRead(destinationMintKey),
      createRead(sourceTokenProgramKey),
      createRead(destinationTokenProgramKey),
      createRead(poolTokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, TOKEN_SWAP_V2_DISCRIMINATOR);
  }

  public static final Discriminator SENCHA_SWAP_DISCRIMINATOR = toDiscriminator(25, 50, 7, 21, 207, 248, 230, 194);

  public static Instruction senchaSwap(final AccountMeta invokedJupiterProgramMeta,
                                       final PublicKey swapProgramKey,
                                       final PublicKey tokenProgramKey,
                                       final PublicKey swapKey,
                                       final PublicKey userAuthorityKey,
                                       final PublicKey inputUserAccountKey,
                                       final PublicKey inputTokenAccountKey,
                                       final PublicKey inputFeesAccountKey,
                                       final PublicKey outputUserAccountKey,
                                       final PublicKey outputTokenAccountKey,
                                       final PublicKey outputFeesAccountKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(tokenProgramKey),
      createWrite(swapKey),
      createRead(userAuthorityKey),
      createWrite(inputUserAccountKey),
      createWrite(inputTokenAccountKey),
      createWrite(inputFeesAccountKey),
      createWrite(outputUserAccountKey),
      createWrite(outputTokenAccountKey),
      createWrite(outputFeesAccountKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, SENCHA_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator STEP_SWAP_DISCRIMINATOR = toDiscriminator(155, 56, 208, 198, 27, 61, 149, 233);

  public static Instruction stepSwap(final AccountMeta invokedJupiterProgramMeta,
                                     final PublicKey tokenSwapProgramKey,
                                     final PublicKey tokenProgramKey,
                                     final PublicKey swapKey,
                                     final PublicKey authorityKey,
                                     final PublicKey userTransferAuthorityKey,
                                     final PublicKey sourceKey,
                                     final PublicKey swapSourceKey,
                                     final PublicKey swapDestinationKey,
                                     final PublicKey destinationKey,
                                     final PublicKey poolMintKey,
                                     final PublicKey poolFeeKey) {
    final var keys = List.of(
      createRead(tokenSwapProgramKey),
      createRead(tokenProgramKey),
      createRead(swapKey),
      createRead(authorityKey),
      createRead(userTransferAuthorityKey),
      createWrite(sourceKey),
      createWrite(swapSourceKey),
      createWrite(swapDestinationKey),
      createWrite(destinationKey),
      createWrite(poolMintKey),
      createWrite(poolFeeKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, STEP_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator CROPPER_SWAP_DISCRIMINATOR = toDiscriminator(230, 216, 47, 182, 165, 117, 210, 103);

  public static Instruction cropperSwap(final AccountMeta invokedJupiterProgramMeta,
                                        final PublicKey tokenSwapProgramKey,
                                        final PublicKey tokenProgramKey,
                                        final PublicKey swapKey,
                                        final PublicKey swapStateKey,
                                        final PublicKey authorityKey,
                                        final PublicKey userTransferAuthorityKey,
                                        final PublicKey sourceKey,
                                        final PublicKey swapSourceKey,
                                        final PublicKey swapDestinationKey,
                                        final PublicKey destinationKey,
                                        final PublicKey poolMintKey,
                                        final PublicKey poolFeeKey) {
    final var keys = List.of(
      createRead(tokenSwapProgramKey),
      createRead(tokenProgramKey),
      createRead(swapKey),
      createRead(swapStateKey),
      createRead(authorityKey),
      createRead(userTransferAuthorityKey),
      createWrite(sourceKey),
      createWrite(swapSourceKey),
      createWrite(swapDestinationKey),
      createWrite(destinationKey),
      createWrite(poolMintKey),
      createWrite(poolFeeKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, CROPPER_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator RAYDIUM_SWAP_DISCRIMINATOR = toDiscriminator(177, 173, 42, 240, 184, 4, 124, 81);

  public static Instruction raydiumSwap(final AccountMeta invokedJupiterProgramMeta,
                                        final PublicKey swapProgramKey,
                                        final PublicKey tokenProgramKey,
                                        final PublicKey ammIdKey,
                                        final PublicKey ammAuthorityKey,
                                        final PublicKey ammOpenOrdersKey,
                                        final PublicKey poolCoinTokenAccountKey,
                                        final PublicKey poolPcTokenAccountKey,
                                        final PublicKey serumProgramIdKey,
                                        final PublicKey serumMarketKey,
                                        final PublicKey serumBidsKey,
                                        final PublicKey serumAsksKey,
                                        final PublicKey serumEventQueueKey,
                                        final PublicKey serumCoinVaultAccountKey,
                                        final PublicKey serumPcVaultAccountKey,
                                        final PublicKey serumVaultSignerKey,
                                        final PublicKey userSourceTokenAccountKey,
                                        final PublicKey userDestinationTokenAccountKey,
                                        final PublicKey userSourceOwnerKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(tokenProgramKey),
      createWrite(ammIdKey),
      createRead(ammAuthorityKey),
      createWrite(ammOpenOrdersKey),
      createWrite(poolCoinTokenAccountKey),
      createWrite(poolPcTokenAccountKey),
      createRead(serumProgramIdKey),
      createWrite(serumMarketKey),
      createWrite(serumBidsKey),
      createWrite(serumAsksKey),
      createWrite(serumEventQueueKey),
      createWrite(serumCoinVaultAccountKey),
      createWrite(serumPcVaultAccountKey),
      createRead(serumVaultSignerKey),
      createWrite(userSourceTokenAccountKey),
      createWrite(userDestinationTokenAccountKey),
      createRead(userSourceOwnerKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, RAYDIUM_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator CREMA_SWAP_DISCRIMINATOR = toDiscriminator(169, 220, 41, 250, 35, 190, 133, 198);

  public static Instruction cremaSwap(final AccountMeta invokedJupiterProgramMeta,
                                      final PublicKey swapProgramKey,
                                      final PublicKey clmmConfigKey,
                                      final PublicKey clmmpoolKey,
                                      final PublicKey tokenAKey,
                                      final PublicKey tokenBKey,
                                      final PublicKey accountAKey,
                                      final PublicKey accountBKey,
                                      final PublicKey tokenAVaultKey,
                                      final PublicKey tokenBVaultKey,
                                      final PublicKey tickArrayMapKey,
                                      final PublicKey ownerKey,
                                      final PublicKey partnerKey,
                                      final PublicKey partnerAtaAKey,
                                      final PublicKey partnerAtaBKey,
                                      final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(clmmConfigKey),
      createWrite(clmmpoolKey),
      createRead(tokenAKey),
      createRead(tokenBKey),
      createWrite(accountAKey),
      createWrite(accountBKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createWrite(tickArrayMapKey),
      createRead(ownerKey),
      createRead(partnerKey),
      createWrite(partnerAtaAKey),
      createWrite(partnerAtaBKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, CREMA_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator LIFINITY_SWAP_DISCRIMINATOR = toDiscriminator(23, 96, 165, 33, 90, 214, 96, 153);

  public static Instruction lifinitySwap(final AccountMeta invokedJupiterProgramMeta,
                                         final PublicKey swapProgramKey,
                                         final PublicKey authorityKey,
                                         final PublicKey ammKey,
                                         final PublicKey userTransferAuthorityKey,
                                         final PublicKey sourceInfoKey,
                                         final PublicKey destinationInfoKey,
                                         final PublicKey swapSourceKey,
                                         final PublicKey swapDestinationKey,
                                         final PublicKey poolMintKey,
                                         final PublicKey feeAccountKey,
                                         final PublicKey tokenProgramKey,
                                         final PublicKey pythAccountKey,
                                         final PublicKey pythPcAccountKey,
                                         final PublicKey configAccountKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(authorityKey),
      createRead(ammKey),
      createRead(userTransferAuthorityKey),
      createWrite(sourceInfoKey),
      createWrite(destinationInfoKey),
      createWrite(swapSourceKey),
      createWrite(swapDestinationKey),
      createWrite(poolMintKey),
      createWrite(feeAccountKey),
      createRead(tokenProgramKey),
      createRead(pythAccountKey),
      createRead(pythPcAccountKey),
      createWrite(configAccountKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, LIFINITY_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator MARINADE_DEPOSIT_DISCRIMINATOR = toDiscriminator(62, 236, 248, 28, 222, 232, 182, 73);

  public static Instruction marinadeDeposit(final AccountMeta invokedJupiterProgramMeta,
                                            final PublicKey marinadeFinanceProgramKey,
                                            final PublicKey stateKey,
                                            final PublicKey msolMintKey,
                                            final PublicKey liqPoolSolLegPdaKey,
                                            final PublicKey liqPoolMsolLegKey,
                                            final PublicKey liqPoolMsolLegAuthorityKey,
                                            final PublicKey reservePdaKey,
                                            final PublicKey transferFromKey,
                                            final PublicKey mintToKey,
                                            final PublicKey msolMintAuthorityKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey tokenProgramKey,
                                            final PublicKey userWsolTokenAccountKey,
                                            final PublicKey tempWsolTokenAccountKey,
                                            final PublicKey userTransferAuthorityKey,
                                            final PublicKey payerKey,
                                            final PublicKey wsolMintKey,
                                            final PublicKey rentKey) {
    final var keys = List.of(
      createRead(marinadeFinanceProgramKey),
      createWrite(stateKey),
      createWrite(msolMintKey),
      createWrite(liqPoolSolLegPdaKey),
      createWrite(liqPoolMsolLegKey),
      createRead(liqPoolMsolLegAuthorityKey),
      createWrite(reservePdaKey),
      createWrite(transferFromKey),
      createWrite(mintToKey),
      createRead(msolMintAuthorityKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createWrite(userWsolTokenAccountKey),
      createWrite(tempWsolTokenAccountKey),
      createRead(userTransferAuthorityKey),
      createWrite(payerKey),
      createRead(wsolMintKey),
      createRead(rentKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, MARINADE_DEPOSIT_DISCRIMINATOR);
  }

  public static final Discriminator MARINADE_UNSTAKE_DISCRIMINATOR = toDiscriminator(41, 120, 15, 0, 113, 219, 42, 1);

  public static Instruction marinadeUnstake(final AccountMeta invokedJupiterProgramMeta,
                                            final PublicKey marinadeFinanceProgramKey,
                                            final PublicKey stateKey,
                                            final PublicKey msolMintKey,
                                            final PublicKey liqPoolSolLegPdaKey,
                                            final PublicKey liqPoolMsolLegKey,
                                            final PublicKey treasuryMsolAccountKey,
                                            final PublicKey getMsolFromKey,
                                            final PublicKey getMsolFromAuthorityKey,
                                            final PublicKey transferSolToKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey tokenProgramKey,
                                            final PublicKey userWsolTokenAccountKey) {
    final var keys = List.of(
      createRead(marinadeFinanceProgramKey),
      createWrite(stateKey),
      createWrite(msolMintKey),
      createWrite(liqPoolSolLegPdaKey),
      createWrite(liqPoolMsolLegKey),
      createWrite(treasuryMsolAccountKey),
      createWrite(getMsolFromKey),
      createRead(getMsolFromAuthorityKey),
      createWrite(transferSolToKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createWrite(userWsolTokenAccountKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, MARINADE_UNSTAKE_DISCRIMINATOR);
  }

  public static final Discriminator ALDRIN_SWAP_DISCRIMINATOR = toDiscriminator(251, 232, 119, 166, 225, 185, 169, 161);

  public static Instruction aldrinSwap(final AccountMeta invokedJupiterProgramMeta,
                                       final PublicKey swapProgramKey,
                                       final PublicKey poolKey,
                                       final PublicKey poolSignerKey,
                                       final PublicKey poolMintKey,
                                       final PublicKey baseTokenVaultKey,
                                       final PublicKey quoteTokenVaultKey,
                                       final PublicKey feePoolTokenAccountKey,
                                       final PublicKey walletAuthorityKey,
                                       final PublicKey userBaseTokenAccountKey,
                                       final PublicKey userQuoteTokenAccountKey,
                                       final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(poolKey),
      createRead(poolSignerKey),
      createWrite(poolMintKey),
      createWrite(baseTokenVaultKey),
      createWrite(quoteTokenVaultKey),
      createWrite(feePoolTokenAccountKey),
      createRead(walletAuthorityKey),
      createWrite(userBaseTokenAccountKey),
      createWrite(userQuoteTokenAccountKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, ALDRIN_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator ALDRIN_V2_SWAP_DISCRIMINATOR = toDiscriminator(190, 166, 89, 139, 33, 152, 16, 10);

  public static Instruction aldrinV2Swap(final AccountMeta invokedJupiterProgramMeta,
                                         final PublicKey swapProgramKey,
                                         final PublicKey poolKey,
                                         final PublicKey poolSignerKey,
                                         final PublicKey poolMintKey,
                                         final PublicKey baseTokenVaultKey,
                                         final PublicKey quoteTokenVaultKey,
                                         final PublicKey feePoolTokenAccountKey,
                                         final PublicKey walletAuthorityKey,
                                         final PublicKey userBaseTokenAccountKey,
                                         final PublicKey userQuoteTokenAccountKey,
                                         final PublicKey curveKey,
                                         final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(poolKey),
      createRead(poolSignerKey),
      createWrite(poolMintKey),
      createWrite(baseTokenVaultKey),
      createWrite(quoteTokenVaultKey),
      createWrite(feePoolTokenAccountKey),
      createRead(walletAuthorityKey),
      createWrite(userBaseTokenAccountKey),
      createWrite(userQuoteTokenAccountKey),
      createRead(curveKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, ALDRIN_V2_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator WHIRLPOOL_SWAP_DISCRIMINATOR = toDiscriminator(123, 229, 184, 63, 12, 0, 92, 145);

  public static Instruction whirlpoolSwap(final AccountMeta invokedJupiterProgramMeta,
                                          final PublicKey swapProgramKey,
                                          final PublicKey tokenProgramKey,
                                          final PublicKey tokenAuthorityKey,
                                          final PublicKey whirlpoolKey,
                                          final PublicKey tokenOwnerAccountAKey,
                                          final PublicKey tokenVaultAKey,
                                          final PublicKey tokenOwnerAccountBKey,
                                          final PublicKey tokenVaultBKey,
                                          final PublicKey tickArray0Key,
                                          final PublicKey tickArray1Key,
                                          final PublicKey tickArray2Key,
                                          // Oracle is currently unused and will be enabled on subsequent updates
                                          final PublicKey oracleKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(tokenProgramKey),
      createRead(tokenAuthorityKey),
      createWrite(whirlpoolKey),
      createWrite(tokenOwnerAccountAKey),
      createWrite(tokenVaultAKey),
      createWrite(tokenOwnerAccountBKey),
      createWrite(tokenVaultBKey),
      createWrite(tickArray0Key),
      createWrite(tickArray1Key),
      createWrite(tickArray2Key),
      createRead(oracleKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, WHIRLPOOL_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator WHIRLPOOL_SWAP_V2_DISCRIMINATOR = toDiscriminator(56, 166, 129, 9, 157, 205, 118, 217);

  public static Instruction whirlpoolSwapV2(final AccountMeta invokedJupiterProgramMeta,
                                            final PublicKey swapProgramKey,
                                            final PublicKey tokenProgramAKey,
                                            final PublicKey tokenProgramBKey,
                                            final PublicKey memoProgramKey,
                                            final PublicKey tokenAuthorityKey,
                                            final PublicKey whirlpoolKey,
                                            final PublicKey tokenMintAKey,
                                            final PublicKey tokenMintBKey,
                                            final PublicKey tokenOwnerAccountAKey,
                                            final PublicKey tokenVaultAKey,
                                            final PublicKey tokenOwnerAccountBKey,
                                            final PublicKey tokenVaultBKey,
                                            final PublicKey tickArray0Key,
                                            final PublicKey tickArray1Key,
                                            final PublicKey tickArray2Key,
                                            // Oracle is currently unused and will be enabled on subsequent updates
                                            final PublicKey oracleKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(tokenProgramAKey),
      createRead(tokenProgramBKey),
      createRead(memoProgramKey),
      createRead(tokenAuthorityKey),
      createWrite(whirlpoolKey),
      createRead(tokenMintAKey),
      createRead(tokenMintBKey),
      createWrite(tokenOwnerAccountAKey),
      createWrite(tokenVaultAKey),
      createWrite(tokenOwnerAccountBKey),
      createWrite(tokenVaultBKey),
      createWrite(tickArray0Key),
      createWrite(tickArray1Key),
      createWrite(tickArray2Key),
      createWrite(oracleKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, WHIRLPOOL_SWAP_V2_DISCRIMINATOR);
  }

  public static final Discriminator INVARIANT_SWAP_DISCRIMINATOR = toDiscriminator(187, 193, 40, 121, 47, 73, 144, 177);

  public static Instruction invariantSwap(final AccountMeta invokedJupiterProgramMeta,
                                          final PublicKey swapProgramKey,
                                          final PublicKey stateKey,
                                          final PublicKey poolKey,
                                          final PublicKey tickmapKey,
                                          final PublicKey accountXKey,
                                          final PublicKey accountYKey,
                                          final PublicKey reserveXKey,
                                          final PublicKey reserveYKey,
                                          final PublicKey ownerKey,
                                          final PublicKey programAuthorityKey,
                                          final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(stateKey),
      createWrite(poolKey),
      createWrite(tickmapKey),
      createWrite(accountXKey),
      createWrite(accountYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(ownerKey),
      createRead(programAuthorityKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, INVARIANT_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator METEORA_SWAP_DISCRIMINATOR = toDiscriminator(127, 125, 226, 12, 81, 24, 204, 35);

  public static Instruction meteoraSwap(final AccountMeta invokedJupiterProgramMeta,
                                        final PublicKey swapProgramKey,
                                        final PublicKey poolKey,
                                        final PublicKey userSourceTokenKey,
                                        final PublicKey userDestinationTokenKey,
                                        final PublicKey aVaultKey,
                                        final PublicKey bVaultKey,
                                        final PublicKey aTokenVaultKey,
                                        final PublicKey bTokenVaultKey,
                                        final PublicKey aVaultLpMintKey,
                                        final PublicKey bVaultLpMintKey,
                                        final PublicKey aVaultLpKey,
                                        final PublicKey bVaultLpKey,
                                        final PublicKey adminTokenFeeKey,
                                        final PublicKey userKey,
                                        final PublicKey vaultProgramKey,
                                        final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createWrite(poolKey),
      createWrite(userSourceTokenKey),
      createWrite(userDestinationTokenKey),
      createWrite(aVaultKey),
      createWrite(bVaultKey),
      createWrite(aTokenVaultKey),
      createWrite(bTokenVaultKey),
      createWrite(aVaultLpMintKey),
      createWrite(bVaultLpMintKey),
      createWrite(aVaultLpKey),
      createWrite(bVaultLpKey),
      createWrite(adminTokenFeeKey),
      createRead(userKey),
      createRead(vaultProgramKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, METEORA_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator GOOSEFX_SWAP_DISCRIMINATOR = toDiscriminator(222, 136, 46, 123, 189, 125, 124, 122);

  public static Instruction goosefxSwap(final AccountMeta invokedJupiterProgramMeta,
                                        final PublicKey swapProgramKey,
                                        final PublicKey controllerKey,
                                        final PublicKey pairKey,
                                        final PublicKey sslInKey,
                                        final PublicKey sslOutKey,
                                        final PublicKey liabilityVaultInKey,
                                        final PublicKey swappedLiabilityVaultInKey,
                                        final PublicKey liabilityVaultOutKey,
                                        final PublicKey swappedLiabilityVaultOutKey,
                                        final PublicKey userInAtaKey,
                                        final PublicKey userOutAtaKey,
                                        final PublicKey feeCollectorAtaKey,
                                        final PublicKey userWalletKey,
                                        final PublicKey feeCollectorKey,
                                        final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(controllerKey),
      createWrite(pairKey),
      createWrite(sslInKey),
      createWrite(sslOutKey),
      createWrite(liabilityVaultInKey),
      createWrite(swappedLiabilityVaultInKey),
      createWrite(liabilityVaultOutKey),
      createWrite(swappedLiabilityVaultOutKey),
      createWrite(userInAtaKey),
      createWrite(userOutAtaKey),
      createWrite(feeCollectorAtaKey),
      createRead(userWalletKey),
      createRead(feeCollectorKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, GOOSEFX_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator DELTAFI_SWAP_DISCRIMINATOR = toDiscriminator(132, 230, 102, 120, 205, 9, 237, 190);

  public static Instruction deltafiSwap(final AccountMeta invokedJupiterProgramMeta,
                                        final PublicKey swapProgramKey,
                                        final PublicKey marketConfigKey,
                                        final PublicKey swapInfoKey,
                                        final PublicKey userSourceTokenKey,
                                        final PublicKey userDestinationTokenKey,
                                        final PublicKey swapSourceTokenKey,
                                        final PublicKey swapDestinationTokenKey,
                                        final PublicKey deltafiUserKey,
                                        final PublicKey adminDestinationTokenKey,
                                        final PublicKey pythPriceBaseKey,
                                        final PublicKey pythPriceQuoteKey,
                                        final PublicKey userAuthorityKey,
                                        final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(marketConfigKey),
      createWrite(swapInfoKey),
      createWrite(userSourceTokenKey),
      createWrite(userDestinationTokenKey),
      createWrite(swapSourceTokenKey),
      createWrite(swapDestinationTokenKey),
      createWrite(deltafiUserKey),
      createWrite(adminDestinationTokenKey),
      createRead(pythPriceBaseKey),
      createRead(pythPriceQuoteKey),
      createRead(userAuthorityKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, DELTAFI_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator BALANSOL_SWAP_DISCRIMINATOR = toDiscriminator(137, 109, 253, 253, 70, 109, 11, 100);

  public static Instruction balansolSwap(final AccountMeta invokedJupiterProgramMeta,
                                         final PublicKey swapProgramKey,
                                         final PublicKey authorityKey,
                                         final PublicKey poolKey,
                                         final PublicKey taxManKey,
                                         final PublicKey bidMintKey,
                                         final PublicKey treasurerKey,
                                         final PublicKey srcTreasuryKey,
                                         final PublicKey srcAssociatedTokenAccountKey,
                                         final PublicKey askMintKey,
                                         final PublicKey dstTreasuryKey,
                                         final PublicKey dstAssociatedTokenAccountKey,
                                         final PublicKey dstTokenAccountTaxmanKey,
                                         final PublicKey systemProgramKey,
                                         final PublicKey tokenProgramKey,
                                         final PublicKey associatedTokenProgramKey,
                                         final PublicKey rentKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createWrite(authorityKey),
      createWrite(poolKey),
      createWrite(taxManKey),
      createRead(bidMintKey),
      createRead(treasurerKey),
      createWrite(srcTreasuryKey),
      createWrite(srcAssociatedTokenAccountKey),
      createRead(askMintKey),
      createWrite(dstTreasuryKey),
      createWrite(dstAssociatedTokenAccountKey),
      createWrite(dstTokenAccountTaxmanKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(rentKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, BALANSOL_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator MARCO_POLO_SWAP_DISCRIMINATOR = toDiscriminator(241, 147, 94, 15, 58, 108, 179, 68);

  public static Instruction marcoPoloSwap(final AccountMeta invokedJupiterProgramMeta,
                                          final PublicKey swapProgramKey,
                                          final PublicKey stateKey,
                                          final PublicKey poolKey,
                                          final PublicKey tokenXKey,
                                          final PublicKey tokenYKey,
                                          final PublicKey poolXAccountKey,
                                          final PublicKey poolYAccountKey,
                                          final PublicKey swapperXAccountKey,
                                          final PublicKey swapperYAccountKey,
                                          final PublicKey swapperKey,
                                          final PublicKey referrerXAccountKey,
                                          final PublicKey referrerYAccountKey,
                                          final PublicKey referrerKey,
                                          final PublicKey programAuthorityKey,
                                          final PublicKey systemProgramKey,
                                          final PublicKey tokenProgramKey,
                                          final PublicKey associatedTokenProgramKey,
                                          final PublicKey rentKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(stateKey),
      createWrite(poolKey),
      createRead(tokenXKey),
      createRead(tokenYKey),
      createWrite(poolXAccountKey),
      createWrite(poolYAccountKey),
      createWrite(swapperXAccountKey),
      createWrite(swapperYAccountKey),
      createWrite(swapperKey),
      createWrite(referrerXAccountKey),
      createWrite(referrerYAccountKey),
      createWrite(referrerKey),
      createRead(programAuthorityKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(rentKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, MARCO_POLO_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator DRADEX_SWAP_DISCRIMINATOR = toDiscriminator(34, 146, 160, 38, 51, 85, 58, 151);

  public static Instruction dradexSwap(final AccountMeta invokedJupiterProgramMeta,
                                       final PublicKey swapProgramKey,
                                       final PublicKey pairKey,
                                       final PublicKey marketKey,
                                       final PublicKey eventQueueKey,
                                       final PublicKey dexUserKey,
                                       final PublicKey marketUserKey,
                                       final PublicKey bidsKey,
                                       final PublicKey asksKey,
                                       final PublicKey t0VaultKey,
                                       final PublicKey t1VaultKey,
                                       final PublicKey t0UserKey,
                                       final PublicKey t1UserKey,
                                       final PublicKey masterKey,
                                       final PublicKey signerKey,
                                       final PublicKey systemProgramKey,
                                       final PublicKey tokenProgramKey,
                                       final PublicKey loggerKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createWrite(pairKey),
      createWrite(marketKey),
      createWrite(eventQueueKey),
      createRead(dexUserKey),
      createWrite(marketUserKey),
      createWrite(bidsKey),
      createWrite(asksKey),
      createWrite(t0VaultKey),
      createWrite(t1VaultKey),
      createWrite(t0UserKey),
      createWrite(t1UserKey),
      createRead(masterKey),
      createWrite(signerKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(loggerKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, DRADEX_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator LIFINITY_V2_SWAP_DISCRIMINATOR = toDiscriminator(19, 152, 195, 245, 187, 144, 74, 227);

  public static Instruction lifinityV2Swap(final AccountMeta invokedJupiterProgramMeta,
                                           final PublicKey swapProgramKey,
                                           final PublicKey authorityKey,
                                           final PublicKey ammKey,
                                           final PublicKey userTransferAuthorityKey,
                                           final PublicKey sourceInfoKey,
                                           final PublicKey destinationInfoKey,
                                           final PublicKey swapSourceKey,
                                           final PublicKey swapDestinationKey,
                                           final PublicKey poolMintKey,
                                           final PublicKey feeAccountKey,
                                           final PublicKey tokenProgramKey,
                                           final PublicKey oracleMainAccountKey,
                                           final PublicKey oracleSubAccountKey,
                                           final PublicKey oraclePcAccountKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(authorityKey),
      createWrite(ammKey),
      createRead(userTransferAuthorityKey),
      createWrite(sourceInfoKey),
      createWrite(destinationInfoKey),
      createWrite(swapSourceKey),
      createWrite(swapDestinationKey),
      createWrite(poolMintKey),
      createWrite(feeAccountKey),
      createRead(tokenProgramKey),
      createRead(oracleMainAccountKey),
      createRead(oracleSubAccountKey),
      createRead(oraclePcAccountKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, LIFINITY_V2_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator RAYDIUM_CLMM_SWAP_DISCRIMINATOR = toDiscriminator(47, 184, 213, 193, 35, 210, 87, 4);

  public static Instruction raydiumClmmSwap(final AccountMeta invokedJupiterProgramMeta,
                                            final PublicKey swapProgramKey,
                                            final PublicKey payerKey,
                                            final PublicKey ammConfigKey,
                                            final PublicKey poolStateKey,
                                            final PublicKey inputTokenAccountKey,
                                            final PublicKey outputTokenAccountKey,
                                            final PublicKey inputVaultKey,
                                            final PublicKey outputVaultKey,
                                            final PublicKey observationStateKey,
                                            final PublicKey tokenProgramKey,
                                            final PublicKey tickArrayKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(payerKey),
      createRead(ammConfigKey),
      createWrite(poolStateKey),
      createWrite(inputTokenAccountKey),
      createWrite(outputTokenAccountKey),
      createWrite(inputVaultKey),
      createWrite(outputVaultKey),
      createWrite(observationStateKey),
      createRead(tokenProgramKey),
      createWrite(tickArrayKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, RAYDIUM_CLMM_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator RAYDIUM_CLMM_SWAP_V2_DISCRIMINATOR = toDiscriminator(86, 108, 246, 93, 88, 47, 114, 90);

  public static Instruction raydiumClmmSwapV2(final AccountMeta invokedJupiterProgramMeta,
                                              final PublicKey swapProgramKey,
                                              final PublicKey payerKey,
                                              final PublicKey ammConfigKey,
                                              final PublicKey poolStateKey,
                                              final PublicKey inputTokenAccountKey,
                                              final PublicKey outputTokenAccountKey,
                                              final PublicKey inputVaultKey,
                                              final PublicKey outputVaultKey,
                                              final PublicKey observationStateKey,
                                              final PublicKey tokenProgramKey,
                                              final PublicKey tokenProgram2022Key,
                                              final PublicKey memoProgramKey,
                                              final PublicKey inputVaultMintKey,
                                              final PublicKey outputVaultMintKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(payerKey),
      createRead(ammConfigKey),
      createWrite(poolStateKey),
      createWrite(inputTokenAccountKey),
      createWrite(outputTokenAccountKey),
      createWrite(inputVaultKey),
      createWrite(outputVaultKey),
      createWrite(observationStateKey),
      createRead(tokenProgramKey),
      createRead(tokenProgram2022Key),
      createRead(memoProgramKey),
      createRead(inputVaultMintKey),
      createRead(outputVaultMintKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, RAYDIUM_CLMM_SWAP_V2_DISCRIMINATOR);
  }

  public static final Discriminator PHOENIX_SWAP_DISCRIMINATOR = toDiscriminator(99, 66, 223, 95, 236, 131, 26, 140);

  public static Instruction phoenixSwap(final AccountMeta invokedJupiterProgramMeta,
                                        final PublicKey swapProgramKey,
                                        final PublicKey logAuthorityKey,
                                        final PublicKey marketKey,
                                        final PublicKey traderKey,
                                        final PublicKey baseAccountKey,
                                        final PublicKey quoteAccountKey,
                                        final PublicKey baseVaultKey,
                                        final PublicKey quoteVaultKey,
                                        final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(logAuthorityKey),
      createWrite(marketKey),
      createRead(traderKey),
      createWrite(baseAccountKey),
      createWrite(quoteAccountKey),
      createWrite(baseVaultKey),
      createWrite(quoteVaultKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, PHOENIX_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator SYMMETRY_SWAP_DISCRIMINATOR = toDiscriminator(17, 114, 237, 234, 154, 12, 185, 116);

  public static Instruction symmetrySwap(final AccountMeta invokedJupiterProgramMeta,
                                         final PublicKey swapProgramKey,
                                         final PublicKey buyerKey,
                                         final PublicKey fundStateKey,
                                         final PublicKey pdaAccountKey,
                                         final PublicKey pdaFromTokenAccountKey,
                                         final PublicKey buyerFromTokenAccountKey,
                                         final PublicKey pdaToTokenAccountKey,
                                         final PublicKey buyerToTokenAccountKey,
                                         final PublicKey swapFeeAccountKey,
                                         final PublicKey hostFeeAccountKey,
                                         final PublicKey managerFeeAccountKey,
                                         final PublicKey tokenListKey,
                                         final PublicKey prismDataKey,
                                         final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(buyerKey),
      createWrite(fundStateKey),
      createRead(pdaAccountKey),
      createWrite(pdaFromTokenAccountKey),
      createWrite(buyerFromTokenAccountKey),
      createWrite(pdaToTokenAccountKey),
      createWrite(buyerToTokenAccountKey),
      createWrite(swapFeeAccountKey),
      createWrite(hostFeeAccountKey),
      createWrite(managerFeeAccountKey),
      createRead(tokenListKey),
      createRead(prismDataKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, SYMMETRY_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator HELIUM_TREASURY_MANAGEMENT_REDEEM_V0_DISCRIMINATOR = toDiscriminator(163, 159, 163, 25, 243, 161, 108, 74);

  public static Instruction heliumTreasuryManagementRedeemV0(final AccountMeta invokedJupiterProgramMeta,
                                                             final PublicKey swapProgramKey,
                                                             final PublicKey treasuryManagementKey,
                                                             final PublicKey treasuryMintKey,
                                                             final PublicKey supplyMintKey,
                                                             final PublicKey treasuryKey,
                                                             final PublicKey circuitBreakerKey,
                                                             final PublicKey fromKey,
                                                             final PublicKey toKey,
                                                             final PublicKey ownerKey,
                                                             final PublicKey circuitBreakerProgramKey,
                                                             final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(treasuryManagementKey),
      createRead(treasuryMintKey),
      createWrite(supplyMintKey),
      createWrite(treasuryKey),
      createWrite(circuitBreakerKey),
      createWrite(fromKey),
      createWrite(toKey),
      createRead(ownerKey),
      createRead(circuitBreakerProgramKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, HELIUM_TREASURY_MANAGEMENT_REDEEM_V0_DISCRIMINATOR);
  }

  public static final Discriminator GOOSEFX_V2_SWAP_DISCRIMINATOR = toDiscriminator(178, 108, 208, 137, 154, 194, 168, 213);

  public static Instruction goosefxV2Swap(final AccountMeta invokedJupiterProgramMeta,
                                          final PublicKey swapProgramKey,
                                          final PublicKey pairKey,
                                          final PublicKey poolRegistryKey,
                                          final PublicKey userWalletKey,
                                          final PublicKey sslPoolInSignerKey,
                                          final PublicKey sslPoolOutSignerKey,
                                          final PublicKey userAtaInKey,
                                          final PublicKey userAtaOutKey,
                                          final PublicKey sslOutMainVaultKey,
                                          final PublicKey sslOutSecondaryVaultKey,
                                          final PublicKey sslInMainVaultKey,
                                          final PublicKey sslInSecondaryVaultKey,
                                          final PublicKey sslOutFeeVaultKey,
                                          final PublicKey feeDestinationKey,
                                          final PublicKey outputTokenPriceHistoryKey,
                                          final PublicKey outputTokenOracleKey,
                                          final PublicKey inputTokenPriceHistoryKey,
                                          final PublicKey inputTokenOracleKey,
                                          final PublicKey eventEmitterKey,
                                          final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createWrite(pairKey),
      createWrite(poolRegistryKey),
      createRead(userWalletKey),
      createRead(sslPoolInSignerKey),
      createRead(sslPoolOutSignerKey),
      createWrite(userAtaInKey),
      createWrite(userAtaOutKey),
      createWrite(sslOutMainVaultKey),
      createWrite(sslOutSecondaryVaultKey),
      createWrite(sslInMainVaultKey),
      createWrite(sslInSecondaryVaultKey),
      createWrite(sslOutFeeVaultKey),
      createWrite(feeDestinationKey),
      createWrite(outputTokenPriceHistoryKey),
      createRead(outputTokenOracleKey),
      createWrite(inputTokenPriceHistoryKey),
      createRead(inputTokenOracleKey),
      createWrite(eventEmitterKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, GOOSEFX_V2_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator PERPS_SWAP_DISCRIMINATOR = toDiscriminator(147, 22, 108, 178, 110, 18, 171, 34);

  public static Instruction perpsSwap(final AccountMeta invokedJupiterProgramMeta,
                                      final PublicKey swapProgramKey,
                                      final PublicKey ownerKey,
                                      final PublicKey fundingAccountKey,
                                      final PublicKey receivingAccountKey,
                                      final PublicKey transferAuthorityKey,
                                      final PublicKey perpetualsKey,
                                      final PublicKey poolKey,
                                      final PublicKey receivingCustodyKey,
                                      final PublicKey receivingCustodyOracleAccountKey,
                                      final PublicKey receivingCustodyTokenAccountKey,
                                      final PublicKey dispensingCustodyKey,
                                      final PublicKey dispensingCustodyOracleAccountKey,
                                      final PublicKey dispensingCustodyTokenAccountKey,
                                      final PublicKey tokenProgramKey,
                                      final PublicKey eventAuthorityKey,
                                      final PublicKey programKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createWrite(ownerKey),
      createWrite(fundingAccountKey),
      createWrite(receivingAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(receivingCustodyKey),
      createRead(receivingCustodyOracleAccountKey),
      createWrite(receivingCustodyTokenAccountKey),
      createWrite(dispensingCustodyKey),
      createRead(dispensingCustodyOracleAccountKey),
      createWrite(dispensingCustodyTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, PERPS_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator PERPS_ADD_LIQUIDITY_DISCRIMINATOR = toDiscriminator(170, 238, 222, 214, 245, 202, 108, 155);

  public static Instruction perpsAddLiquidity(final AccountMeta invokedJupiterProgramMeta,
                                              final PublicKey swapProgramKey,
                                              final PublicKey ownerKey,
                                              final PublicKey fundingOrReceivingAccountKey,
                                              final PublicKey lpTokenAccountKey,
                                              final PublicKey transferAuthorityKey,
                                              final PublicKey perpetualsKey,
                                              final PublicKey poolKey,
                                              final PublicKey custodyKey,
                                              final PublicKey custodyOracleAccountKey,
                                              final PublicKey custodyTokenAccountKey,
                                              final PublicKey lpTokenMintKey,
                                              final PublicKey tokenProgramKey,
                                              final PublicKey eventAuthorityKey,
                                              final PublicKey programKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createWrite(ownerKey),
      createWrite(fundingOrReceivingAccountKey),
      createWrite(lpTokenAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(custodyKey),
      createRead(custodyOracleAccountKey),
      createWrite(custodyTokenAccountKey),
      createWrite(lpTokenMintKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, PERPS_ADD_LIQUIDITY_DISCRIMINATOR);
  }

  public static final Discriminator PERPS_REMOVE_LIQUIDITY_DISCRIMINATOR = toDiscriminator(79, 211, 232, 140, 8, 78, 220, 34);

  public static Instruction perpsRemoveLiquidity(final AccountMeta invokedJupiterProgramMeta,
                                                 final PublicKey swapProgramKey,
                                                 final PublicKey ownerKey,
                                                 final PublicKey fundingOrReceivingAccountKey,
                                                 final PublicKey lpTokenAccountKey,
                                                 final PublicKey transferAuthorityKey,
                                                 final PublicKey perpetualsKey,
                                                 final PublicKey poolKey,
                                                 final PublicKey custodyKey,
                                                 final PublicKey custodyOracleAccountKey,
                                                 final PublicKey custodyTokenAccountKey,
                                                 final PublicKey lpTokenMintKey,
                                                 final PublicKey tokenProgramKey,
                                                 final PublicKey eventAuthorityKey,
                                                 final PublicKey programKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createWrite(ownerKey),
      createWrite(fundingOrReceivingAccountKey),
      createWrite(lpTokenAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(custodyKey),
      createRead(custodyOracleAccountKey),
      createWrite(custodyTokenAccountKey),
      createWrite(lpTokenMintKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, PERPS_REMOVE_LIQUIDITY_DISCRIMINATOR);
  }

  public static final Discriminator METEORA_DLMM_SWAP_DISCRIMINATOR = toDiscriminator(127, 64, 37, 138, 173, 243, 207, 84);

  public static Instruction meteoraDlmmSwap(final AccountMeta invokedJupiterProgramMeta,
                                            final PublicKey swapProgramKey,
                                            final PublicKey lbPairKey,
                                            final PublicKey binArrayBitmapExtensionKey,
                                            final PublicKey reserveXKey,
                                            final PublicKey reserveYKey,
                                            final PublicKey userTokenInKey,
                                            final PublicKey userTokenOutKey,
                                            final PublicKey tokenXMintKey,
                                            final PublicKey tokenYMintKey,
                                            final PublicKey oracleKey,
                                            final PublicKey hostFeeInKey,
                                            final PublicKey userKey,
                                            final PublicKey tokenXProgramKey,
                                            final PublicKey tokenYProgramKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createWrite(lbPairKey),
      createRead(binArrayBitmapExtensionKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(userTokenInKey),
      createWrite(userTokenOutKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(oracleKey),
      createRead(hostFeeInKey),
      createRead(userKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, METEORA_DLMM_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator OPEN_BOOK_V2_SWAP_DISCRIMINATOR = toDiscriminator(135, 26, 163, 43, 198, 221, 29, 67);

  public static Instruction openBookV2Swap(final AccountMeta invokedJupiterProgramMeta,
                                           final PublicKey swapProgramKey,
                                           final PublicKey signerKey,
                                           final PublicKey penaltyPayerKey,
                                           final PublicKey marketKey,
                                           final PublicKey marketAuthorityKey,
                                           final PublicKey bidsKey,
                                           final PublicKey asksKey,
                                           final PublicKey marketBaseVaultKey,
                                           final PublicKey marketQuoteVaultKey,
                                           final PublicKey eventHeapKey,
                                           final PublicKey userBaseAccountKey,
                                           final PublicKey userQuoteAccountKey,
                                           final PublicKey oracleAKey,
                                           final PublicKey oracleBKey,
                                           final PublicKey tokenProgramKey,
                                           final PublicKey systemProgramKey,
                                           final PublicKey openOrdersAdminKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createWrite(signerKey),
      createWrite(penaltyPayerKey),
      createWrite(marketKey),
      createRead(marketAuthorityKey),
      createWrite(bidsKey),
      createWrite(asksKey),
      createWrite(marketBaseVaultKey),
      createWrite(marketQuoteVaultKey),
      createWrite(eventHeapKey),
      createWrite(userBaseAccountKey),
      createWrite(userQuoteAccountKey),
      createRead(oracleAKey),
      createRead(oracleBKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(openOrdersAdminKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, OPEN_BOOK_V2_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator CLONE_SWAP_DISCRIMINATOR = toDiscriminator(85, 201, 154, 92, 133, 31, 142, 85);

  public static Instruction cloneSwap(final AccountMeta invokedJupiterProgramMeta,
                                      final PublicKey swapProgramKey,
                                      final PublicKey userKey,
                                      final PublicKey cloneKey,
                                      final PublicKey poolsKey,
                                      final PublicKey oraclesKey,
                                      final PublicKey userCollateralTokenAccountKey,
                                      final PublicKey userOnassetTokenAccountKey,
                                      final PublicKey onassetMintKey,
                                      final PublicKey collateralMintKey,
                                      final PublicKey collateralVaultKey,
                                      final PublicKey treasuryOnassetTokenAccountKey,
                                      final PublicKey treasuryCollateralTokenAccountKey,
                                      final PublicKey tokenProgramKey,
                                      final PublicKey cloneStakingKey,
                                      final PublicKey userStakingAccountKey,
                                      final PublicKey cloneStakingProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(userKey),
      createWrite(cloneKey),
      createWrite(poolsKey),
      createWrite(oraclesKey),
      createWrite(userCollateralTokenAccountKey),
      createWrite(userOnassetTokenAccountKey),
      createWrite(onassetMintKey),
      createRead(collateralMintKey),
      createWrite(collateralVaultKey),
      createWrite(treasuryOnassetTokenAccountKey),
      createWrite(treasuryCollateralTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(cloneStakingKey),
      createRead(userStakingAccountKey),
      createRead(cloneStakingProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, CLONE_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator RAYDIUM_CP_SWAP_DISCRIMINATOR = toDiscriminator(54, 234, 83, 141, 52, 191, 46, 144);

  public static Instruction raydiumCpSwap(final AccountMeta invokedJupiterProgramMeta,
                                          final PublicKey swapProgramKey,
                                          final PublicKey payerKey,
                                          final PublicKey authorityKey,
                                          final PublicKey ammConfigKey,
                                          final PublicKey poolStateKey,
                                          final PublicKey inputTokenAccountKey,
                                          final PublicKey outputTokenAccountKey,
                                          final PublicKey inputVaultKey,
                                          final PublicKey outputVaultKey,
                                          final PublicKey inputTokenProgramKey,
                                          final PublicKey outputTokenProgramKey,
                                          final PublicKey inputTokenMintKey,
                                          final PublicKey outputTokenMintKey,
                                          final PublicKey observationStateKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(payerKey),
      createRead(authorityKey),
      createRead(ammConfigKey),
      createWrite(poolStateKey),
      createWrite(inputTokenAccountKey),
      createWrite(outputTokenAccountKey),
      createWrite(inputVaultKey),
      createWrite(outputVaultKey),
      createRead(inputTokenProgramKey),
      createRead(outputTokenProgramKey),
      createRead(inputTokenMintKey),
      createRead(outputTokenMintKey),
      createWrite(observationStateKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, RAYDIUM_CP_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator ONE_INTRO_SWAP_DISCRIMINATOR = toDiscriminator(208, 212, 80, 169, 36, 148, 209, 35);

  public static Instruction oneIntroSwap(final AccountMeta invokedJupiterProgramMeta,
                                         final PublicKey swapProgramKey,
                                         final PublicKey metadataStateKey,
                                         final PublicKey poolStateKey,
                                         final PublicKey poolAuthPdaKey,
                                         final PublicKey poolTokenInAccountKey,
                                         final PublicKey poolTokenOutAccountKey,
                                         final PublicKey userKey,
                                         final PublicKey userTokenInAccountKey,
                                         final PublicKey userTokenOutAccountKey,
                                         final PublicKey metadataSwapFeeAccountKey,
                                         final PublicKey referralTokenAccountKey,
                                         final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(metadataStateKey),
      createWrite(poolStateKey),
      createRead(poolAuthPdaKey),
      createWrite(poolTokenInAccountKey),
      createWrite(poolTokenOutAccountKey),
      createWrite(userKey),
      createWrite(userTokenInAccountKey),
      createWrite(userTokenOutAccountKey),
      createWrite(metadataSwapFeeAccountKey),
      createWrite(referralTokenAccountKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, ONE_INTRO_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator PUMPDOTFUN_WRAPPED_BUY_DISCRIMINATOR = toDiscriminator(138, 139, 167, 134, 208, 91, 138, 158);

  public static Instruction pumpdotfunWrappedBuy(final AccountMeta invokedJupiterProgramMeta,
                                                 final PublicKey swapProgramKey,
                                                 final PublicKey globalKey,
                                                 final PublicKey feeRecipientKey,
                                                 final PublicKey mintKey,
                                                 final PublicKey bondingCurveKey,
                                                 final PublicKey associatedBondingCurveKey,
                                                 final PublicKey associatedUserKey,
                                                 final PublicKey userKey,
                                                 final PublicKey systemProgramKey,
                                                 final PublicKey tokenProgramKey,
                                                 final PublicKey rentKey,
                                                 final PublicKey eventAuthorityKey,
                                                 final PublicKey programKey,
                                                 final PublicKey userWsolTokenAccountKey,
                                                 final PublicKey tempWsolTokenAccountKey,
                                                 final PublicKey wsolMintKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(globalKey),
      createWrite(feeRecipientKey),
      createRead(mintKey),
      createWrite(bondingCurveKey),
      createWrite(associatedBondingCurveKey),
      createWrite(associatedUserKey),
      createWrite(userKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(rentKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createWrite(userWsolTokenAccountKey),
      createWrite(tempWsolTokenAccountKey),
      createRead(wsolMintKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, PUMPDOTFUN_WRAPPED_BUY_DISCRIMINATOR);
  }

  public static final Discriminator PUMPDOTFUN_WRAPPED_SELL_DISCRIMINATOR = toDiscriminator(255, 19, 99, 99, 40, 65, 83, 255);

  public static Instruction pumpdotfunWrappedSell(final AccountMeta invokedJupiterProgramMeta,
                                                  final PublicKey swapProgramKey,
                                                  final PublicKey globalKey,
                                                  final PublicKey feeRecipientKey,
                                                  final PublicKey mintKey,
                                                  final PublicKey bondingCurveKey,
                                                  final PublicKey associatedBondingCurveKey,
                                                  final PublicKey associatedUserKey,
                                                  final PublicKey userKey,
                                                  final PublicKey systemProgramKey,
                                                  final PublicKey associatedTokenProgramKey,
                                                  final PublicKey tokenProgramKey,
                                                  final PublicKey eventAuthorityKey,
                                                  final PublicKey programKey,
                                                  final PublicKey userWsolTokenAccountKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(globalKey),
      createWrite(feeRecipientKey),
      createRead(mintKey),
      createWrite(bondingCurveKey),
      createWrite(associatedBondingCurveKey),
      createWrite(associatedUserKey),
      createWrite(userKey),
      createRead(systemProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createWrite(userWsolTokenAccountKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, PUMPDOTFUN_WRAPPED_SELL_DISCRIMINATOR);
  }

  public static final Discriminator PERPS_V2_SWAP_DISCRIMINATOR = toDiscriminator(127, 245, 19, 158, 82, 250, 33, 18);

  public static Instruction perpsV2Swap(final AccountMeta invokedJupiterProgramMeta,
                                        final PublicKey swapProgramKey,
                                        final PublicKey ownerKey,
                                        final PublicKey fundingAccountKey,
                                        final PublicKey receivingAccountKey,
                                        final PublicKey transferAuthorityKey,
                                        final PublicKey perpetualsKey,
                                        final PublicKey poolKey,
                                        final PublicKey receivingCustodyKey,
                                        final PublicKey receivingCustodyDovesPriceAccountKey,
                                        final PublicKey receivingCustodyPythnetPriceAccountKey,
                                        final PublicKey receivingCustodyTokenAccountKey,
                                        final PublicKey dispensingCustodyKey,
                                        final PublicKey dispensingCustodyDovesPriceAccountKey,
                                        final PublicKey dispensingCustodyPythnetPriceAccountKey,
                                        final PublicKey dispensingCustodyTokenAccountKey,
                                        final PublicKey tokenProgramKey,
                                        final PublicKey eventAuthorityKey,
                                        final PublicKey programKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(ownerKey),
      createWrite(fundingAccountKey),
      createWrite(receivingAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(receivingCustodyKey),
      createRead(receivingCustodyDovesPriceAccountKey),
      createRead(receivingCustodyPythnetPriceAccountKey),
      createWrite(receivingCustodyTokenAccountKey),
      createWrite(dispensingCustodyKey),
      createRead(dispensingCustodyDovesPriceAccountKey),
      createRead(dispensingCustodyPythnetPriceAccountKey),
      createWrite(dispensingCustodyTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, PERPS_V2_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator PERPS_V2_ADD_LIQUIDITY_DISCRIMINATOR = toDiscriminator(18, 66, 88, 194, 197, 52, 116, 212);

  public static Instruction perpsV2AddLiquidity(final AccountMeta invokedJupiterProgramMeta,
                                                final PublicKey swapProgramKey,
                                                final PublicKey ownerKey,
                                                final PublicKey fundingOrReceivingAccountKey,
                                                final PublicKey lpTokenAccountKey,
                                                final PublicKey transferAuthorityKey,
                                                final PublicKey perpetualsKey,
                                                final PublicKey poolKey,
                                                final PublicKey custodyKey,
                                                final PublicKey custodyDovesPriceAccountKey,
                                                final PublicKey custodyPythnetPriceAccountKey,
                                                final PublicKey custodyTokenAccountKey,
                                                final PublicKey lpTokenMintKey,
                                                final PublicKey tokenProgramKey,
                                                final PublicKey eventAuthorityKey,
                                                final PublicKey programKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(ownerKey),
      createWrite(fundingOrReceivingAccountKey),
      createWrite(lpTokenAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(custodyKey),
      createRead(custodyDovesPriceAccountKey),
      createRead(custodyPythnetPriceAccountKey),
      createWrite(custodyTokenAccountKey),
      createWrite(lpTokenMintKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, PERPS_V2_ADD_LIQUIDITY_DISCRIMINATOR);
  }

  public static final Discriminator PERPS_V2_REMOVE_LIQUIDITY_DISCRIMINATOR = toDiscriminator(16, 103, 98, 99, 106, 36, 5, 105);

  public static Instruction perpsV2RemoveLiquidity(final AccountMeta invokedJupiterProgramMeta,
                                                   final PublicKey swapProgramKey,
                                                   final PublicKey ownerKey,
                                                   final PublicKey fundingOrReceivingAccountKey,
                                                   final PublicKey lpTokenAccountKey,
                                                   final PublicKey transferAuthorityKey,
                                                   final PublicKey perpetualsKey,
                                                   final PublicKey poolKey,
                                                   final PublicKey custodyKey,
                                                   final PublicKey custodyDovesPriceAccountKey,
                                                   final PublicKey custodyPythnetPriceAccountKey,
                                                   final PublicKey custodyTokenAccountKey,
                                                   final PublicKey lpTokenMintKey,
                                                   final PublicKey tokenProgramKey,
                                                   final PublicKey eventAuthorityKey,
                                                   final PublicKey programKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(ownerKey),
      createWrite(fundingOrReceivingAccountKey),
      createWrite(lpTokenAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(custodyKey),
      createRead(custodyDovesPriceAccountKey),
      createRead(custodyPythnetPriceAccountKey),
      createWrite(custodyTokenAccountKey),
      createWrite(lpTokenMintKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, PERPS_V2_REMOVE_LIQUIDITY_DISCRIMINATOR);
  }

  public static final Discriminator MOONSHOT_WRAPPED_BUY_DISCRIMINATOR = toDiscriminator(207, 150, 213, 156, 138, 104, 238, 142);

  public static Instruction moonshotWrappedBuy(final AccountMeta invokedJupiterProgramMeta,
                                               final PublicKey swapProgramKey,
                                               final PublicKey senderKey,
                                               final PublicKey senderTokenAccountKey,
                                               final PublicKey curveAccountKey,
                                               final PublicKey curveTokenAccountKey,
                                               final PublicKey dexFeeKey,
                                               final PublicKey helioFeeKey,
                                               final PublicKey mintKey,
                                               final PublicKey configAccountKey,
                                               final PublicKey tokenProgramKey,
                                               final PublicKey associatedTokenProgramKey,
                                               final PublicKey systemProgramKey,
                                               final PublicKey userWsolTokenAccountKey,
                                               final PublicKey tempWsolTokenAccountKey,
                                               final PublicKey wsolMintKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createWrite(senderKey),
      createWrite(senderTokenAccountKey),
      createWrite(curveAccountKey),
      createWrite(curveTokenAccountKey),
      createWrite(dexFeeKey),
      createWrite(helioFeeKey),
      createRead(mintKey),
      createRead(configAccountKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createWrite(userWsolTokenAccountKey),
      createWrite(tempWsolTokenAccountKey),
      createRead(wsolMintKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, MOONSHOT_WRAPPED_BUY_DISCRIMINATOR);
  }

  public static final Discriminator MOONSHOT_WRAPPED_SELL_DISCRIMINATOR = toDiscriminator(248, 2, 240, 253, 17, 184, 57, 8);

  public static Instruction moonshotWrappedSell(final AccountMeta invokedJupiterProgramMeta,
                                                final PublicKey swapProgramKey,
                                                final PublicKey senderKey,
                                                final PublicKey senderTokenAccountKey,
                                                final PublicKey curveAccountKey,
                                                final PublicKey curveTokenAccountKey,
                                                final PublicKey dexFeeKey,
                                                final PublicKey helioFeeKey,
                                                final PublicKey mintKey,
                                                final PublicKey configAccountKey,
                                                final PublicKey tokenProgramKey,
                                                final PublicKey associatedTokenProgramKey,
                                                final PublicKey systemProgramKey,
                                                final PublicKey userWsolTokenAccountKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createWrite(senderKey),
      createWrite(senderTokenAccountKey),
      createWrite(curveAccountKey),
      createWrite(curveTokenAccountKey),
      createWrite(dexFeeKey),
      createWrite(helioFeeKey),
      createRead(mintKey),
      createRead(configAccountKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createWrite(userWsolTokenAccountKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, MOONSHOT_WRAPPED_SELL_DISCRIMINATOR);
  }

  public static final Discriminator STABBLE_STABLE_SWAP_DISCRIMINATOR = toDiscriminator(144, 73, 163, 148, 143, 34, 40, 144);

  public static Instruction stabbleStableSwap(final AccountMeta invokedJupiterProgramMeta,
                                              final PublicKey swapProgramKey,
                                              final PublicKey userKey,
                                              final PublicKey userTokenInKey,
                                              final PublicKey userTokenOutKey,
                                              final PublicKey vaultTokenInKey,
                                              final PublicKey vaultTokenOutKey,
                                              final PublicKey beneficiaryTokenOutKey,
                                              final PublicKey poolKey,
                                              final PublicKey withdrawAuthorityKey,
                                              final PublicKey vaultKey,
                                              final PublicKey vaultAuthorityKey,
                                              final PublicKey vaultProgramKey,
                                              final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(userKey),
      createWrite(userTokenInKey),
      createWrite(userTokenOutKey),
      createWrite(vaultTokenInKey),
      createWrite(vaultTokenOutKey),
      createWrite(beneficiaryTokenOutKey),
      createWrite(poolKey),
      createRead(withdrawAuthorityKey),
      createRead(vaultKey),
      createRead(vaultAuthorityKey),
      createRead(vaultProgramKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, STABBLE_STABLE_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator STABBLE_WEIGHTED_SWAP_DISCRIMINATOR = toDiscriminator(94, 214, 232, 111, 142, 61, 123, 29);

  public static Instruction stabbleWeightedSwap(final AccountMeta invokedJupiterProgramMeta,
                                                final PublicKey swapProgramKey,
                                                final PublicKey userKey,
                                                final PublicKey userTokenInKey,
                                                final PublicKey userTokenOutKey,
                                                final PublicKey vaultTokenInKey,
                                                final PublicKey vaultTokenOutKey,
                                                final PublicKey beneficiaryTokenOutKey,
                                                final PublicKey poolKey,
                                                final PublicKey withdrawAuthorityKey,
                                                final PublicKey vaultKey,
                                                final PublicKey vaultAuthorityKey,
                                                final PublicKey vaultProgramKey,
                                                final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createRead(userKey),
      createWrite(userTokenInKey),
      createWrite(userTokenOutKey),
      createWrite(vaultTokenInKey),
      createWrite(vaultTokenOutKey),
      createWrite(beneficiaryTokenOutKey),
      createWrite(poolKey),
      createRead(withdrawAuthorityKey),
      createRead(vaultKey),
      createRead(vaultAuthorityKey),
      createRead(vaultProgramKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, STABBLE_WEIGHTED_SWAP_DISCRIMINATOR);
  }

  public static final Discriminator OBRIC_SWAP_DISCRIMINATOR = toDiscriminator(65, 93, 96, 169, 190, 214, 95, 3);

  public static Instruction obricSwap(final AccountMeta invokedJupiterProgramMeta,
                                      final PublicKey swapProgramKey,
                                      final PublicKey tradingPairKey,
                                      final PublicKey mintXKey,
                                      final PublicKey mintYKey,
                                      final PublicKey reserveXKey,
                                      final PublicKey reserveYKey,
                                      final PublicKey userTokenAccountXKey,
                                      final PublicKey userTokenAccountYKey,
                                      final PublicKey protocolFeeKey,
                                      final PublicKey xPriceFeedKey,
                                      final PublicKey yPriceFeedKey,
                                      final PublicKey userKey,
                                      final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(swapProgramKey),
      createWrite(tradingPairKey),
      createRead(mintXKey),
      createRead(mintYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(userTokenAccountXKey),
      createWrite(userTokenAccountYKey),
      createWrite(protocolFeeKey),
      createRead(xPriceFeedKey),
      createRead(yPriceFeedKey),
      createRead(userKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, OBRIC_SWAP_DISCRIMINATOR);
  }

  private JupiterProgram() {
  }
}
