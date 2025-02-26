package software.sava.anchor.programs.metadao.amm.anchor;

import java.util.List;

import software.sava.anchor.programs.metadao.amm.anchor.types.AddLiquidityArgs;
import software.sava.anchor.programs.metadao.amm.anchor.types.CreateAmmArgs;
import software.sava.anchor.programs.metadao.amm.anchor.types.RemoveLiquidityArgs;
import software.sava.anchor.programs.metadao.amm.anchor.types.SwapArgs;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class AmmProgram {

  public static final Discriminator CREATE_AMM_DISCRIMINATOR = toDiscriminator(242, 91, 21, 170, 5, 68, 125, 64);

  public static Instruction createAmm(final AccountMeta invokedAmmProgramMeta,
                                      final PublicKey userKey,
                                      final PublicKey ammKey,
                                      final PublicKey lpMintKey,
                                      final PublicKey baseMintKey,
                                      final PublicKey quoteMintKey,
                                      final PublicKey vaultAtaBaseKey,
                                      final PublicKey vaultAtaQuoteKey,
                                      final PublicKey associatedTokenProgramKey,
                                      final PublicKey tokenProgramKey,
                                      final PublicKey systemProgramKey,
                                      final PublicKey eventAuthorityKey,
                                      final PublicKey programKey,
                                      final CreateAmmArgs args) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(ammKey),
      createWrite(lpMintKey),
      createRead(baseMintKey),
      createRead(quoteMintKey),
      createWrite(vaultAtaBaseKey),
      createWrite(vaultAtaQuoteKey),
      createRead(associatedTokenProgramKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(args)];
    int i = writeDiscriminator(CREATE_AMM_DISCRIMINATOR, _data, 0);
    Borsh.write(args, _data, i);

    return Instruction.createInstruction(invokedAmmProgramMeta, keys, _data);
  }

  public record CreateAmmIxData(Discriminator discriminator, CreateAmmArgs args) implements Borsh {  

    public static CreateAmmIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static CreateAmmIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var args = CreateAmmArgs.read(_data, i);
      return new CreateAmmIxData(discriminator, args);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(args, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADD_LIQUIDITY_DISCRIMINATOR = toDiscriminator(181, 157, 89, 67, 143, 182, 52, 72);

  public static Instruction addLiquidity(final AccountMeta invokedAmmProgramMeta,
                                         final PublicKey userKey,
                                         final PublicKey ammKey,
                                         final PublicKey lpMintKey,
                                         final PublicKey userLpAccountKey,
                                         final PublicKey userBaseAccountKey,
                                         final PublicKey userQuoteAccountKey,
                                         final PublicKey vaultAtaBaseKey,
                                         final PublicKey vaultAtaQuoteKey,
                                         final PublicKey tokenProgramKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final AddLiquidityArgs args) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(ammKey),
      createWrite(lpMintKey),
      createWrite(userLpAccountKey),
      createWrite(userBaseAccountKey),
      createWrite(userQuoteAccountKey),
      createWrite(vaultAtaBaseKey),
      createWrite(vaultAtaQuoteKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(args)];
    int i = writeDiscriminator(ADD_LIQUIDITY_DISCRIMINATOR, _data, 0);
    Borsh.write(args, _data, i);

    return Instruction.createInstruction(invokedAmmProgramMeta, keys, _data);
  }

  public record AddLiquidityIxData(Discriminator discriminator, AddLiquidityArgs args) implements Borsh {  

    public static AddLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 32;

    public static AddLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var args = AddLiquidityArgs.read(_data, i);
      return new AddLiquidityIxData(discriminator, args);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(args, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REMOVE_LIQUIDITY_DISCRIMINATOR = toDiscriminator(80, 85, 209, 72, 24, 206, 177, 108);

  public static Instruction removeLiquidity(final AccountMeta invokedAmmProgramMeta,
                                            final PublicKey userKey,
                                            final PublicKey ammKey,
                                            final PublicKey lpMintKey,
                                            final PublicKey userLpAccountKey,
                                            final PublicKey userBaseAccountKey,
                                            final PublicKey userQuoteAccountKey,
                                            final PublicKey vaultAtaBaseKey,
                                            final PublicKey vaultAtaQuoteKey,
                                            final PublicKey tokenProgramKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey,
                                            final RemoveLiquidityArgs args) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(ammKey),
      createWrite(lpMintKey),
      createWrite(userLpAccountKey),
      createWrite(userBaseAccountKey),
      createWrite(userQuoteAccountKey),
      createWrite(vaultAtaBaseKey),
      createWrite(vaultAtaQuoteKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(args)];
    int i = writeDiscriminator(REMOVE_LIQUIDITY_DISCRIMINATOR, _data, 0);
    Borsh.write(args, _data, i);

    return Instruction.createInstruction(invokedAmmProgramMeta, keys, _data);
  }

  public record RemoveLiquidityIxData(Discriminator discriminator, RemoveLiquidityArgs args) implements Borsh {  

    public static RemoveLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 32;

    public static RemoveLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var args = RemoveLiquidityArgs.read(_data, i);
      return new RemoveLiquidityIxData(discriminator, args);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(args, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SWAP_DISCRIMINATOR = toDiscriminator(248, 198, 158, 145, 225, 117, 135, 200);

  public static Instruction swap(final AccountMeta invokedAmmProgramMeta,
                                 final PublicKey userKey,
                                 final PublicKey ammKey,
                                 final PublicKey userBaseAccountKey,
                                 final PublicKey userQuoteAccountKey,
                                 final PublicKey vaultAtaBaseKey,
                                 final PublicKey vaultAtaQuoteKey,
                                 final PublicKey tokenProgramKey,
                                 final PublicKey eventAuthorityKey,
                                 final PublicKey programKey,
                                 final SwapArgs args) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(ammKey),
      createWrite(userBaseAccountKey),
      createWrite(userQuoteAccountKey),
      createWrite(vaultAtaBaseKey),
      createWrite(vaultAtaQuoteKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(args)];
    int i = writeDiscriminator(SWAP_DISCRIMINATOR, _data, 0);
    Borsh.write(args, _data, i);

    return Instruction.createInstruction(invokedAmmProgramMeta, keys, _data);
  }

  public record SwapIxData(Discriminator discriminator, SwapArgs args) implements Borsh {  

    public static SwapIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 25;

    public static SwapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var args = SwapArgs.read(_data, i);
      return new SwapIxData(discriminator, args);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(args, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CRANK_THAT_TWAP_DISCRIMINATOR = toDiscriminator(220, 100, 25, 249, 0, 92, 195, 193);

  public static Instruction crankThatTwap(final AccountMeta invokedAmmProgramMeta,
                                          final PublicKey ammKey,
                                          final PublicKey eventAuthorityKey,
                                          final PublicKey programKey) {
    final var keys = List.of(
      createWrite(ammKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedAmmProgramMeta, keys, CRANK_THAT_TWAP_DISCRIMINATOR);
  }

  private AmmProgram() {
  }
}
