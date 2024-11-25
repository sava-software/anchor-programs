package software.sava.anchor.programs.moonshot.anchor;

import java.util.List;

import software.sava.anchor.programs.moonshot.anchor.types.ConfigParams;
import software.sava.anchor.programs.moonshot.anchor.types.TokenMintParams;
import software.sava.anchor.programs.moonshot.anchor.types.TradeParams;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class TokenLaunchpadProgram {

  public static final Discriminator TOKEN_MINT_DISCRIMINATOR = toDiscriminator(3, 44, 164, 184, 123, 13, 245, 179);

  public static Instruction tokenMint(final AccountMeta invokedTokenLaunchpadProgramMeta,
                                      final PublicKey senderKey,
                                      final PublicKey backendAuthorityKey,
                                      final PublicKey curveAccountKey,
                                      final PublicKey mintKey,
                                      // Type validating that the account is owned by the System Program = uninitialized
                                      // seeds should ensure that the address is correct
                                      final PublicKey mintMetadataKey,
                                      final PublicKey curveTokenAccountKey,
                                      final PublicKey configAccountKey,
                                      final PublicKey tokenProgramKey,
                                      final PublicKey associatedTokenProgramKey,
                                      final PublicKey mplTokenMetadataKey,
                                      final PublicKey systemProgramKey,
                                      final TokenMintParams mintParams) {
    final var keys = List.of(
      createWritableSigner(senderKey),
      createReadOnlySigner(backendAuthorityKey),
      createWrite(curveAccountKey),
      createWritableSigner(mintKey),
      createWrite(mintMetadataKey),
      createWrite(curveTokenAccountKey),
      createRead(configAccountKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(mplTokenMetadataKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(mintParams)];
    int i = writeDiscriminator(TOKEN_MINT_DISCRIMINATOR, _data, 0);
    Borsh.write(mintParams, _data, i);

    return Instruction.createInstruction(invokedTokenLaunchpadProgramMeta, keys, _data);
  }

  public record TokenMintIxData(Discriminator discriminator, TokenMintParams mintParams) implements Borsh {  

    public static TokenMintIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static TokenMintIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintParams = TokenMintParams.read(_data, i);
      return new TokenMintIxData(discriminator, mintParams);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(mintParams, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(mintParams);
    }
  }

  public static final Discriminator BUY_DISCRIMINATOR = toDiscriminator(102, 6, 61, 18, 1, 218, 235, 234);

  public static Instruction buy(final AccountMeta invokedTokenLaunchpadProgramMeta,
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
                                final TradeParams data) {
    final var keys = List.of(
      createWritableSigner(senderKey),
      createWrite(senderTokenAccountKey),
      createWrite(curveAccountKey),
      createWrite(curveTokenAccountKey),
      createWrite(dexFeeKey),
      createWrite(helioFeeKey),
      createRead(mintKey),
      createRead(configAccountKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(data)];
    int i = writeDiscriminator(BUY_DISCRIMINATOR, _data, 0);
    Borsh.write(data, _data, i);

    return Instruction.createInstruction(invokedTokenLaunchpadProgramMeta, keys, _data);
  }

  public record BuyIxData(Discriminator discriminator, TradeParams data) implements Borsh {  

    public static BuyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 33;

    public static BuyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var data = TradeParams.read(_data, i);
      return new BuyIxData(discriminator, data);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(data, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SELL_DISCRIMINATOR = toDiscriminator(51, 230, 133, 164, 1, 127, 131, 173);

  public static Instruction sell(final AccountMeta invokedTokenLaunchpadProgramMeta,
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
                                 final TradeParams data) {
    final var keys = List.of(
      createWritableSigner(senderKey),
      createWrite(senderTokenAccountKey),
      createWrite(curveAccountKey),
      createWrite(curveTokenAccountKey),
      createWrite(dexFeeKey),
      createWrite(helioFeeKey),
      createRead(mintKey),
      createRead(configAccountKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(data)];
    int i = writeDiscriminator(SELL_DISCRIMINATOR, _data, 0);
    Borsh.write(data, _data, i);

    return Instruction.createInstruction(invokedTokenLaunchpadProgramMeta, keys, _data);
  }

  public record SellIxData(Discriminator discriminator, TradeParams data) implements Borsh {  

    public static SellIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 33;

    public static SellIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var data = TradeParams.read(_data, i);
      return new SellIxData(discriminator, data);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(data, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MIGRATE_FUNDS_DISCRIMINATOR = toDiscriminator(42, 229, 10, 231, 189, 62, 193, 174);

  public static Instruction migrateFunds(final AccountMeta invokedTokenLaunchpadProgramMeta,
                                         // BE Authority
                                         final PublicKey backendAuthorityKey,
                                         // Migration Authority
                                         // Owner and Payer over Token Accounts, needs to be mutable
                                         final PublicKey migrationAuthorityKey,
                                         // Curve Account
                                         // The account is closed after this instruction
                                         final PublicKey curveAccountKey,
                                         // Curve Token Account
                                         // The account is closed after this instruction
                                         final PublicKey curveTokenAccountKey,
                                         // Authority token Account
                                         // Init on demand
                                         final PublicKey migrationAuthorityTokenAccountKey,
                                         // InterfaceAccount: checks program ownership + deserialize into Mint
                                         final PublicKey mintKey,
                                         final PublicKey dexFeeAccountKey,
                                         final PublicKey helioFeeAccountKey,
                                         final PublicKey configAccountKey,
                                         final PublicKey systemProgramKey,
                                         final PublicKey tokenProgramKey,
                                         final PublicKey associatedTokenProgramKey) {
    final var keys = List.of(
      createReadOnlySigner(backendAuthorityKey),
      createWritableSigner(migrationAuthorityKey),
      createWrite(curveAccountKey),
      createWrite(curveTokenAccountKey),
      createWrite(migrationAuthorityTokenAccountKey),
      createWrite(mintKey),
      createWrite(dexFeeAccountKey),
      createWrite(helioFeeAccountKey),
      createRead(configAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey)
    );

    return Instruction.createInstruction(invokedTokenLaunchpadProgramMeta, keys, MIGRATE_FUNDS_DISCRIMINATOR);
  }

  public static final Discriminator CONFIG_INIT_DISCRIMINATOR = toDiscriminator(13, 236, 164, 173, 106, 253, 164, 185);

  public static Instruction configInit(final AccountMeta invokedTokenLaunchpadProgramMeta,
                                       final PublicKey configAuthorityKey,
                                       final PublicKey configAccountKey,
                                       final PublicKey systemProgramKey,
                                       final ConfigParams data) {
    final var keys = List.of(
      createWritableSigner(configAuthorityKey),
      createWrite(configAccountKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(data)];
    int i = writeDiscriminator(CONFIG_INIT_DISCRIMINATOR, _data, 0);
    Borsh.write(data, _data, i);

    return Instruction.createInstruction(invokedTokenLaunchpadProgramMeta, keys, _data);
  }

  public record ConfigInitIxData(Discriminator discriminator, ConfigParams data) implements Borsh {  

    public static ConfigInitIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ConfigInitIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var data = ConfigParams.read(_data, i);
      return new ConfigInitIxData(discriminator, data);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(data, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(data);
    }
  }

  public static final Discriminator CONFIG_UPDATE_DISCRIMINATOR = toDiscriminator(80, 37, 109, 136, 82, 135, 89, 241);

  public static Instruction configUpdate(final AccountMeta invokedTokenLaunchpadProgramMeta,
                                         final PublicKey configAuthorityKey,
                                         final PublicKey configAccountKey,
                                         final ConfigParams data) {
    final var keys = List.of(
      createReadOnlySigner(configAuthorityKey),
      createWrite(configAccountKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(data)];
    int i = writeDiscriminator(CONFIG_UPDATE_DISCRIMINATOR, _data, 0);
    Borsh.write(data, _data, i);

    return Instruction.createInstruction(invokedTokenLaunchpadProgramMeta, keys, _data);
  }

  public record ConfigUpdateIxData(Discriminator discriminator, ConfigParams data) implements Borsh {  

    public static ConfigUpdateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ConfigUpdateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var data = ConfigParams.read(_data, i);
      return new ConfigUpdateIxData(discriminator, data);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(data, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(data);
    }
  }

  private TokenLaunchpadProgram() {
  }
}
